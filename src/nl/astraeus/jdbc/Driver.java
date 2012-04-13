package nl.astraeus.jdbc;

import nl.astraeus.http.SimpleWebServer;
import nl.astraeus.jdbc.web.JdbcStatisticsServlet;
import nl.astraeus.jdbc.web.ResourceServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Enumeration;
import java.util.Properties;

/**
 * User: riennentjes
 * Date: Jul 10, 2008
 * Time: 8:54:37 PM
 */
public class Driver implements java.sql.Driver {
    private final static Logger log = LoggerFactory.getLogger(Driver.class);

    final private static String URL_PREFIX = "jdbc:stat:";

    private java.sql.Driver driver = null;
    private String[] drivers = {
            "org.postgresql.Driver",
            "oracle.jdbc.driver.OracleDriver",
            "com.sybase.jdbc2.jdbc.SybDriver",
            "net.sourceforge.jtds.jdbc.Driver",
            "com.microsoft.jdbc.sqlserver.SQLServerDriver",
            "com.microsoft.sqlserver.jdbc.SQLServerDriver",
            "weblogic.jdbc.sqlserver.SQLServerDriver",
            "com.informix.jdbc.IfxDriver",
            "org.apache.derby.jdbc.ClientDriver",
            "org.apache.derby.jdbc.EmbeddedDriver",
            "com.mysql.jdbc.Driver",
            "org.hsqldb.jdbcDriver",
            "org.h2.Driver" };

    static {
        log.debug("Loading driver class " + Driver.class.getName());

        try {
            DriverManager.registerDriver(new Driver());
        } catch (SQLException e) {
            log.error("", e);
        }
    }

    public Driver() {
        for (String dr : drivers) {
            try {
                Class.forName(dr);
            } catch (Throwable e) {
                log.debug("Can't instantiate driver: " + dr, e);
            }
        }
    }

    private java.sql.Driver findDriver(String url) throws SQLException {
        Enumeration e = DriverManager.getDrivers();

        while (e.hasMoreElements()) {
            java.sql.Driver d = (java.sql.Driver) e.nextElement();

            if (d.acceptsURL(url)) {
                return d;
            }
        }

        throw new SQLException("Driver not found: " + url);
    }

    public Connection connect(String url, Properties info) throws SQLException {
        url = url.substring(URL_PREFIX.length());

        if (driver == null) {
            driver = findDriver(url);
        }

        if (driver != null) {
            SimpleWebServer server = new SimpleWebServer(18080);

            server.addServlet(new JdbcStatisticsServlet(), "/");
            server.addServlet(new ResourceServlet(), "/resources/*");

            server.start();
        }

        return new ConnectionLogger(driver.connect(url, info));
    }

    public boolean acceptsURL(String url) throws SQLException {
        return (url != null && url.startsWith(URL_PREFIX));
    }

    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return driver.getPropertyInfo(url, info);
    }

    public int getMajorVersion() {
        return driver.getMajorVersion();
    }

    public int getMinorVersion() {
        return driver.getMinorVersion();
    }

    public boolean jdbcCompliant() {
        return driver.jdbcCompliant();
    }

    public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return driver.getParentLogger();
    }
}
