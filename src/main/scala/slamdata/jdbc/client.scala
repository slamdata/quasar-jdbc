package slamdata.jdbc

import scalaz._, Scalaz._
import argonaut._, Argonaut._
import dispatch._, Defaults._

class Client(val url: String) {
  private val svc: Req = dispatch.url(url)
  
  def query(sql: String, vars: Map[String, String] = Map.empty, maxRows: Option[Int] = None): String \/ List[Json] = {
    val outPath = "tmp/out"  // HACK

    def post: String \/ Json = {
      val req = (svc / "query" / "fs" / "").POST.setHeader("Destination", outPath) <<? vars << sql
  
      val exec = Http(req OK as.String)
      for {
        rez  <- \/.fromTryCatchNonFatal(exec()).leftMap(_.toString)  // FIXME
        json <- Parse.parse(rez)
     } yield json
   }
 
   def get(path: String): String \/ List[Json] = {
     val req = svc / "data" / "fs" / "tmp" / "out"  // HACK
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
  
     resp2 <- get(path)
    } yield resp2
  }
}