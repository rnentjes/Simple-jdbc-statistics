package nl.astraeus.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import nl.astraeus.jdbc.web.JdbcStatsMappingProvider;
import nl.astraeus.jdbc.web.model.Settings;
import nl.astraeus.web.NanoHttpdSimpleWeb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: riennentjes
 * Date: Jul 10, 2008
 * Time: 8:54:37 PM
 *
 * Test a little change
 *
 */
public class Driver implements java.sql.Driver {
    private final static Logger log = LoggerFactory.getLogger(Driver.class);

    final private static String URL_PREFIX          = "jdbc:stat:";
    final private static String URL_SECURE_PREFIX   = "jdbc:secstat:";

    public static class StatsLogger {
        private volatile boolean started    = false;
        private NanoHttpdSimpleWeb server   = null;
        private java.sql.Driver driver      = null;
        private Settings settings           = new Settings();
        private String targetUrl            = null;

        public Settings getSettings() {
            return settings;
        }
    }

    private static Map<String, StatsLogger>     loggers     = new ConcurrentHashMap<String, StatsLogger>();
    private static Map<Integer, StatsLogger>    portMapping = new ConcurrentHashMap<Integer, StatsLogger>();

    public static StatsLogger get(String url) {
        if (!loggers.containsKey(url)) {
            throw new IllegalArgumentException("No jdbc statistics logger found for url: "+url);
        }

        return loggers.get(url);
    }

    public static StatsLogger get(int port) {
        if (!portMapping.containsKey(port)) {
            throw new IllegalArgumentException("No jdbc statistics logger found on port: "+port);
        }

        return portMapping.get(port);
    }

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
        StatsLogger logger = loggers.get(url);

        if (logger == null) {
            logger = new StatsLogger();

            if (url.startsWith(URL_PREFIX)) {
                logger.settings.setSecure(false);
            } else if (url.startsWith(URL_SECURE_PREFIX)) {
                logger.settings.setSecure(true);
            }

            String [] parts = url.split("\\:");
            String settingsString = "webServerConnections=5;numberOfQueries=2500;logStacktraces=true;formattedQueries=true";

            if (parts.length > 3) {
                settingsString = parts[2];
            }

            logger.settings.parseSettings(settingsString);

            StringBuilder targetUrl = new StringBuilder();
            for (int index = 3; index < parts.length; index++) {
                if (index > 3) {
                    targetUrl.append(":");
                }
                targetUrl.append(parts[index]);
            }
            logger.targetUrl = targetUrl.toString();

            if (logger.driver == null) {
                logger.driver = findDriver(targetUrl.toString());
            }

            if (logger.settings.isSecure()) {
                String user = info.getProperty("user");
                String password = info.getProperty("password");

                if (user == null || password == null) {
                    log.warn("User and/or password not found in jdbc connection information!");
                } else {
                    logger.settings.setUser(user);
                    logger.settings.setPasswordHash(password.hashCode());
                }
            }

            if (logger.driver != null && !logger.started) {
                synchronized (this) {
                    if (!logger.started) {
                        try {
                            NanoHttpdSimpleWeb server = new NanoHttpdSimpleWeb(logger.settings.getWebServerPort(), new JdbcStatsMappingProvider());

                            server.start();

                            System.out.println("Started Simple JDBC Statistics\n\turl: "+url+"\n\tport: "+logger.settings.getWebServerPort());

                            logger.started = true;
                        } catch (Exception e) {
                            log.error(e.getMessage(),e);
                        }
                    }
                }
            }

            loggers.put(url, logger);
            portMapping.put(logger.settings.getWebServerPort(), logger);
        }

        return new ConnectionLogger(JdbcLogger.get(logger.settings.getWebServerPort()), logger.driver.connect(logger.targetUrl, info));
    }

    public boolean acceptsURL(String url) throws SQLException {
        return (url != null && (url.startsWith(URL_PREFIX) || url.startsWith(URL_SECURE_PREFIX)));
    }

    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        StatsLogger logger = loggers.get(url);

        if (logger != null) {
            return logger.driver.getPropertyInfo(url, info);
        }

        return new DriverPropertyInfo[0];
    }

    public int getMajorVersion() {
        return 1;
    }

    public int getMinorVersion() {
        return 0;
    }

    public boolean jdbcCompliant() {
        return true;
    }

    public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new SQLFeatureNotSupportedException("Not supported with simple-jdbc-statistics.");
    }
}
