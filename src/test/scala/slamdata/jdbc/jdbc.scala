package slamdata.jdbc

import org.specs2.mutable._

class DriverSpecs extends Specification {
  "SlamDataDriver" should {

    "connect to local server" in {
      val driver = new SlamDataDriver()
      
      val cxn = driver.connect("http://localhost:8080/", null)
            
      cxn.close()
      
      success
    }

    "run simple query" in {
      val driver = new SlamDataDriver()
      
      val cxn = driver.connect("http://localhost:8080/", null)
      try {

        val stmt = cxn.createStatement
      
        val rs = stmt.executeQuery("select count(*) from zips")
      
        val hasNext = rs.next
      
        // val count = rs.getInt("0")
        //
        // count must_== 29533
        
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