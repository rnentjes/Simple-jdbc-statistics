package nl.astraeus.jdbc.web.page;

import nl.astraeus.jdbc.web.JdbcStatsMapping;

/**
 * User: rnentjes
 * Date: 4/22/12
 * Time: 8:25 PM
 */
public class Login extends StatsPage {
    private boolean warn = false;

    private String login = "";

    @Override
    public void post() {
        warn = false;

        if ("save".equals(getParameter("action"))) {
            login = getParameter("login");
            String password = getParameter("password");

            if (login == null) {
                login = "";
            }

            if (getSettings().validUser(login, password)) {
                setSession("loggedin", true);

                redirect(JdbcStatsMapping.QUERIES);
            } else {
                warn = true;
            }
        }

        set();
    }

    public void set() {
        set("login", login);
        set("warn", warn);
    }
}
