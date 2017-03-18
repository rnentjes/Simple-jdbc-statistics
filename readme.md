# Simple jdbc statistics

A simple wrapper around jdbc drivers that will show some statistics about the running queries on a simple web page on port 18080.

See a running example here (just a demo, might be down): http://www.astraeus.nl:18100/

Screenshot:

![Screenshot](/data/sjs-screenshot.png "Screenshot")

## Download:

[simple-jdbc-stats-nodep-1.5.7.jar](https://github.com/rnentjes/Simple-jdbc-statistics/releases/download/1.5.7/simple-jdbc-stats-nodep-1.5.7.jar)

## Maven, gradle etc.

Add maven repository: https://nexus.astraeus.nl/nexus/content/groups/public

Pom:

```xml
<dependency>
  <groupId>nl.astraeus</groupId>
  <artifactId>simple-jdbc-stats</artifactId>
  <version>1.5.7</version>
</dependency>
```

## How to use:

Add the jar to your classpath:

* [simple-jdbc-stats-nodep-1.5.7.jar](https://github.com/rnentjes/Simple-jdbc-statistics/releases/download/1.5.7/simple-jdbc-stats-nodep-1.5.7.jar)

Set your jdbc driver property to the following class:

```java
  nl.astraeus.jdbc.Driver
```

Add the following to the front of your current jdbc url:

```text
  jdbc:stat:<settings>:
  
  (eg. jdbc:postgresql://localhost/mydb becomes: jdbc:stat::jdbc:postgresql://localhost/mydb)
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

If your driver is not in there, make sure it's known before you connect to the database (eg Class.forName("&lt;driver class name&gt;"); )

There are some settings that can be passed in the jdbc url. There is a settings menu where you can change them at runtime and it will show you an example for your jdbc url.

Example (default values): webServerPort=18080;webServerConnections=2;numberOfQueries=2500;logStacktraces=true;formattedQueries=true
