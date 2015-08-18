# SlamData JDBC Driver

Thin JDBC driver for the SlamData Engine, supporting query execution by
connecting to the SlamData Engine.


## Building from Source

**Note:** Requires Java 7.

### Checkout

```bash
git clone git@github.com:slamdata/slamengine.git
```

### Build

Run the tests and assemble the driver and its dependencies into a single jar:
```bash
sbt assembly
```


## Usage

The SlamData Engine API server must be running and accessible via the network. See
[slamdata/slamengine](/slamdata/slamengine).

You open a connection using a URL made up of the scheme `slamengine`, the 
host name and port of the SlamData server, and the path within the SlamData 
filesystem where your source files are found.

For example `slamengine://localhost:8080/test/`.


### From Java

Add `slamengine-jdbc_2.11-0.1-SNAPSHOT.jar` to your classpath.

```java
import java.sql.*;

...

Driver driver = new slamdata.jdbc.SlamDataDriver();
      
Connection cxn = driver.connect("slamengine://localhost:8080/test/", null);
try {
  Statement stmt = cxn.createStatement();

  ResultSet rs = stmt.executeQuery("select * from zips");
  while (rs.next()) {
    for (int i=1; i <= rs.getColumnCount(); i++) {
      if (i > 1) System.out.print("; ");
      System.out.print(rs.getString(i));
    }
    System.out.println();
  }
}
finally {
  cxn.close();
}
```

Note: error handling and resource cleanup elided above.

### With any JDBC-compatible tool

Configure your tool to use `slamengine-jdbc_2.11-0.1-SNAPSHOT.jar`.

Open a connection with a URL like `http://localhost:8080/test/`.

Run queries...


## Legal

Released under ??? LICENSE. See [LICENSE](/slamdata/slamengine-jdbc/blob/master/LICENSE).

Copyright 2014-2015 SlamData Inc.