A simple wrapper around jdbc drivers that will show some statistics about the running queries on a simple web page on port 18080.

To use:

Add the following jars to your classpath:

* jdbc-stats-0.1.jar
* slf4j-api-1.6.4.jar (add a slf4j binding if you want to see some logging)
* ssr-0.1.jar
* vst-0.4.jar

Add the following jar if you are not running in a web container:

* servlet-api-2.5.jar

Set your jdbc driver property to the following class:

```java
  nl.astraeus.jdbc.Driver
```

Add the following to the front of your current jdbc url:

```text
  jdbc:stat:
  
  (eg. jdbc:postgresql://localhost/mydb becomes: jdbc:stat:jdbc:postgresql://localhost/mydb)
```

Start your application and goto: http://&lt;host app is running on&gt;:18080/

You will see an overview of the last 2500 queries run on your database and some timing stats about them.

Drivers automatically discovered atm:

```java
  org.postgresql.Driver
  oracle.jdbc.driver.OracleDriver
  com.sybase.jdbc2.jdbc.SybDriver
  net.sourceforge.jtds.jdbc.Driver
  com.microsoft.jdbc.sqlserver.SQLServerDriver
  com.microsoft.sqlserver.jdbc.SQLServerDriver
  weblogic.jdbc.sqlserver.SQLServerDriver
  com.informix.jdbc.IfxDriver
  org.apache.derby.jdbc.ClientDriver
  org.apache.derby.jdbc.EmbeddedDriver
  com.mysql.jdbc.Driver
  org.hsqldb.jdbcDriver
  org.h2.Driver
```

If yours driver is not in there, make sure it's known before you connect to the database (eg Class.forName("&lt;driver class name&gt;"); )