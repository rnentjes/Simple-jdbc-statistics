package nl.astraeus.jdbc.web.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: rnentjes
 * Date: 4/22/12
 * Time: 1:53 PM
 */
public class Settings {
    private final static Logger logger = LoggerFactory.getLogger(Settings.class);

    private static final Settings instance = new Settings();

    public static Settings get() {
        return instance;
    }

    public void readSettings(HttpServletRequest request) {
        Cookie [] cookies = request.getCookies();

        for (Cookie c : cookies) {
            if (c.getName().equals("SJS-number-of-queries")) {
                setNumberOfQueries(Integer.parseInt(c.getValue()));
            }
            if (c.getName().equals("SJS-formatted-queries")) {
                setFormattedQueries("true".equals(c.getValue()));
            }
        }
    }

    public void saveSettings(HttpServletResponse response) {
        response.addCookie(createLongLivedCookie("SJS-number-of-queries", String.valueOf(getNumberOfQueries())));
        response.addCookie(createLongLivedCookie("SJS-formatted-queries", String.valueOf(isFormattedQueries())));
    }

    private Cookie createLongLivedCookie(String name, String value) {
        Cookie result = new Cookie(name, value);

        // 90 days
        result.setMaxAge(60 * 60 * 24 * 90);

        return result;
    }

    private int numberOfQueries = 2500;
    private boolean formattedQueries = false;
    private boolean secure = true;
    private String user = null;
    private int passwordHash = 0;

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

}
