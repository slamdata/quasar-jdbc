/*
 * Copyright 2014 - 2015 SlamData Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package quasar.jdbc

import scalaz._, Scalaz._
import argonaut._, Argonaut._
import dispatch._, Defaults._

class Client(val url: String) {
  private val parsedUrl = new java.net.URI(url)

  private def host = parsedUrl.getHost
  private def port = if (parsedUrl.getPort < 0) 20223 else parsedUrl.getPort
  private def base = parsedUrl.getPath

  private def root = dispatch.url("http://" + host + ":" + port.toString + "/")

  def info: String \/ Json = {
    val req = root / "server" / "info"

    val exec = Http(req OK as.String)
    for {
      rez <- \/.fromTryCatchNonFatal(exec()).leftMap(_.toString)  // FIXME
      json <- Parse.parse(rez)
    } yield json
  }

  def query(sql: String, vars: Map[String, String] = Map.empty, maxRows: Option[Int] = None): String \/ List[Json] = {
    def post: String \/ Json = {
      val req = (root / "query" / "fs" / base).POST.setHeader("Destination", "tmp0") <<? vars << sql

      val exec = Http(req OK as.String)
      for {
        rez  <- \/.fromTryCatchNonFatal(exec()).leftMap(_.toString)  // FIXME
        json <- Parse.parse(rez)
     } yield json
   }

   def get(path: String): String \/ List[Json] = {
     val req = (root / "data" / "fs" / base) / path
     val limited = maxRows.map { max => req <<? Map("limit" -> max.toString) }.getOrElse(req)

     val exec = Http(limited OK as.String)
     for {
       rez  <- \/.fromTryCatchNonFatal(exec()).leftMap(_.toString)  // FIXME
       json <- rez.split("\n").toList.map(Parse.parse).sequenceU
     } yield json
   }

   for {
     resp1 <- post
     path  <- (resp1.hcursor --\ "out").as[String].result.leftMap(_.toString)

     last = path.substring(path.lastIndexOf('/') + 1)
     resp2 <- get(last)
    } yield resp2
  }
}
