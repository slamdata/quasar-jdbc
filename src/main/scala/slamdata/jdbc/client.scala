package slamdata.jdbc

import scalaz._, Scalaz._
import argonaut._, Argonaut._
import dispatch._, Defaults._

class Client(val url: String) {
  private def svc(entity: String): Req = {
    val parsedUrl = new java.net.URI(url)
    val port = if (parsedUrl.getPort < 0) 20223 else parsedUrl.getPort
    dispatch.url("http://" + parsedUrl.getHost + ":" + port.toString + "/" + entity + "/fs/" + parsedUrl.getPath)
  }
  
  def query(sql: String, vars: Map[String, String] = Map.empty, maxRows: Option[Int] = None): String \/ List[Json] = {
    def post: String \/ Json = {
      val req = svc("query").POST.setHeader("Destination", "tmp0") <<? vars << sql
      
      val exec = Http(req OK as.String)
      for {
        rez  <- \/.fromTryCatchNonFatal(exec()).leftMap(_.toString)  // FIXME
        json <- Parse.parse(rez)
     } yield json
   }
 
   def get(path: String): String \/ List[Json] = {
     val req = svc("data") / path
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