# SlamData JDBC Driver

See [slamdata/slamengine](/slamdata/slamengine).


## Requirements

Requires JRE 1.7 or later.


## Using the driver from Java

```java
import java.sql.*;

...

Driver driver = new slamdata.jdbc.SlamDataDriver();
      
Connection cxn = driver.connect("http://localhost:8080/", null);
try {
  Statement stmt = cxn.createStatement();

  ResultSet rs = stmt.executeQuery("select * from zips");
  while (rs.next()) {
    for (int i=1; i <= rs.getColumnCount(); i++) {
      System.out.println(rs.getString(i));
    }
  }
}
finally {
  cxn.close();
}
```