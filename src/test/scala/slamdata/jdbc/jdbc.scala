package slamdata.jdbc

import org.specs2.mutable._

class DriverSpecs extends Specification {
  "SlamDataDriver" should {

    "connect to local server" in {
      val driver = new SlamDataDriver()
      
      val cxn = driver.connect("slamengine://104.236.166.167:8080/demo/", null)
            
      cxn.close()
      
      success
    }

    "run simple query" in {
      val driver = new SlamDataDriver()
      
      val cxn = driver.connect("slamengine://104.236.166.167:8080/demo/", null)
      try {

        val stmt = cxn.createStatement
      
        val rs = stmt.executeQuery("select count(*) from zips")
      
        val hasNext = rs.next
      
        val rez = rs.getString("0")
        rez must_== "29353"
      }
      finally {
        cxn.close()
      }
    }
  }
  
  step {
    // cleanup threads for SBT purposes:
    dispatch.Http.shutdown  
  }
}