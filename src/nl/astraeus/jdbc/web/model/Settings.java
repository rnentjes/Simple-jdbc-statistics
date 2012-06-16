package nl.astraeus.jdbc.web.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;

/**
 * User: rnentjes
 * Date: 4/22/12
 * Time: 1:53 PM
 */
public class Settings {
    private final static Logger logger = LoggerFactory.getLogger(Settings.class);

    private static final Settings instance = new Settings();

    private final static String NUMBER_OF_QUERIES = "numberOfQueries";
    private final static String LOG_STACKTRACES   = "logStacktraces";
    private final static String FORMATTED_QUERIES = "formattedQueries";
    private final static String CONNECTION_POOLS  = "webServerThreads";
    private final static String WEBSERVER_PORT    = "webServerPort";


    public static Settings get() {
        return instance;
    }

    private Cookie createLongLivedCookie(String name, String value) {
        Cookie result = new Cookie(name, value);

        // 90 days
        result.setMaxAge(60 * 60 * 24 * 90);

        return result;
    }

    private int numberOfQueries = 2500;
    private boolean formattedQueries = true;
    private boolean recordingStacktraces = true;
    private int webServerConnections = 2;
    private int webServerPort = 18080;

    private boolean secure = true;
    private String user = null;
    private int passwordHash = 0;

    public int getWebServerPort() {
        return webServerPort;
    }

    public void setWebServerPort(int webServerPort) {
        this.webServerPort = webServerPort;
    }

    public int getWebServerConnections() {
        return webServerConnections;
    }

    public void setWebServerConnections(int webServerConnections) {
        this.webServerConnections = webServerConnections;
    }

    public int getNumberOfQueries() {
        return numberOfQueries;
    }

    public void setNumberOfQueries(int numberOfQueries) {
        this.numberOfQueries = numberOfQueries;
    }

    public boolean isFormattedQueries() {
        return formattedQueries;
    }

    public void setFormattedQueries(boolean formattedQueries) {
        this.formattedQueries = formattedQueries;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public boolean isSecure() {
        return secure;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPasswordHash(int passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean validUser(String user, String password) {
        return (user != null && password != null && user.equals(this.user) && password.hashCode() == passwordHash);
    }

    public boolean isRecordingStacktraces() {
        return recordingStacktraces;
    }

    public void setRecordingStacktraces(boolean recordingStacktraces) {
        this.recordingStacktraces = recordingStacktraces;
    }

    private int getValueAsInt(String value) {
        int result = 0;

        try {
            result = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            logger.debug(e.getMessage(), e);
        }

        return result;
    }

    public void setSettings(String settings) {
        if (settings != null && settings.length() > 0) {
            String [] parts = settings.split("\\;");

            for (String part : parts) {
                String [] subParts = part.split("\\=");

                if (subParts.length != 2) {
                    logger.warn("Unknown setting: "+part);
                } else {
                    String name = subParts[0];
                    String value = subParts[1];

                    if (WEBSERVER_PORT.equalsIgnoreCase(name)) {
                        int port = getValueAsInt(value);

                        if (port > 1024 && port < 65536) {
                            setWebServerPort(port);
                        } else {
                            logger.warn(WEBSERVER_PORT+" has an illegal value, should be a number between 1025 and 65535.");
                        }
                    } else if (CONNECTION_POOLS.equalsIgnoreCase(name)) {
                        int cons = getValueAsInt(value);

                        if (cons > 0 && cons < 26) {
                            setWebServerConnections(cons);
                        } else {
                            logger.warn(CONNECTION_POOLS+" has an illegal value, should be a number between 1 and 25.");
                        }
                    } else if (NUMBER_OF_QUERIES.equalsIgnoreCase(name)) {
                        int nrQueries = getValueAsInt(value);

                        if (nrQueries > 0 && nrQueries < 25001) {
                            setNumberOfQueries(nrQueries);
                        } else {
                            logger.warn(NUMBER_OF_QUERIES + " has an illegal value, should be a number between 1 and 25000.");
                        }
                    } else if (LOG_STACKTRACES.equalsIgnoreCase(name)) {
                        if ("false".equals(value) || "0".equals(value)) {
                            setRecordingStacktraces(false);
                        } else {
                            setRecordingStacktraces(true);
                        }
                    } else if (FORMATTED_QUERIES.equalsIgnoreCase(name)) {
                        if ("false".equals(value) || "0".equals(value)) {
                            setFormattedQueries(false);
                        } else {
                            setFormattedQueries(true);
                        }
                    }
                }
            }
        }
    }

    public String getSettings() {
        StringBuilder result = new StringBuilder();

        result.append(WEBSERVER_PORT);
        result.append("=");
        result.append(this.webServerPort);

        result.append(";");
        result.append(CONNECTION_POOLS);
        result.append("=");
        result.append(this.webServerConnections);

        result.append(";");

        result.append(NUMBER_OF_QUERIES);
        result.append("=");
        result.append(this.numberOfQueries);

        result.append(";");

        result.append(LOG_STACKTRACES);
        result.append("=");
        result.append(this.recordingStacktraces);

        result.append(";");

        result.append(FORMATTED_QUERIES);
        result.append("=");
        result.append(this.formattedQueries);

        return result.toString();
    }
}
