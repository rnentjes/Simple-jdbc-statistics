package nl.astraeus.jdbc;

import nl.astraeus.http.SimpleWebServer;
import nl.astraeus.jdbc.web.JdbcStatisticsServlet;
import nl.astraeus.jdbc.web.ResourceServlet;
import nl.astraeus.jdbc.web.model.Settings;
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
    final private static String URL_SECURE_PREFIX = "jdbc:secstat:";

    private static volatile boolean started = false;
    private static SimpleWebServer server = null;

    public static SimpleWebServer getServer() {
        return server;
    }

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
        if (url.startsWith(URL_PREFIX)) {
            Settings.get().setSecure(false);
        } else if (url.startsWith(URL_SECURE_PREFIX)) {
            Settings.get().setSecure(true);
        }

        String [] parts = url.split("\\:");
        String settings = "webServerConnections=5;numberOfQueries=2500;logStacktraces=true;formattedQueries=true";

        if (parts.length > 3) {
            settings = parts[2];
        }

        Settings.get().setSettings(settings);

        StringBuilder targetUrl = new StringBuilder();
        for (int index = 3; index < parts.length; index++) {
            if (index > 3) {
                targetUrl.append(":");
            }
            targetUrl.append(parts[index]);
        }

        if (driver == null) {
            driver = findDriver(targetUrl.toString());
        }

        if (Settings.get().isSecure()) {
            String user = info.getProperty("user");
            String password = info.getProperty("password");

            if (user == null || password == null) {
                log.warn("User and/or password not found in jdbc connection information!");
            } else {
                Settings.get().setUser(user);
                Settings.get().setPasswordHash(password.hashCode());
            }
        }

        if (driver != null && !started) {
            try {
                server = new SimpleWebServer(Settings.get().getWebServerPort());

                server.addServlet(new JdbcStatisticsServlet(), "/*");
                server.addServlet(new ResourceServlet(), "/resources/*");

                server.setNumberOfConnections(Settings.get().getWebServerConnections());

                server.start();

                System.out.println("Started Simple JDBC Statistics, listening on port: "+Settings.get().getWebServerPort());

                started = true;
            } catch (Exception e) {
                log.error(e.getMessage(),e);
            }
        }

        return new ConnectionLogger(driver.connect(targetUrl.toString(), info));
    }

    public boolean acceptsURL(String url) throws SQLException {
        return (url != null && (url.startsWith(URL_PREFIX) || url.startsWith(URL_SECURE_PREFIX)));
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
