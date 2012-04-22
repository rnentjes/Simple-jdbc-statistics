package nl.astraeus.jdbc.web.page;

import nl.astraeus.jdbc.web.model.Settings;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * User: rnentjes
 * Date: 4/22/12
 * Time: 8:25 PM
 */
public class Login extends TemplatePage {
    private boolean warn = false;

    private String login = "";

    @Override
    public Page processRequest(HttpServletRequest request) {
        Page result = this;
        warn = false;

        if ("save".equals(request.getParameter("action"))) {
            login = request.getParameter("login");
            String password = request.getParameter("password");

            if (login == null) {
                login = "";
            }

            if (Settings.get().validUser(login, password)) {
                request.getSession().setAttribute("loggedin", true);

                result = new QueryOverview();
            } else {
                warn = true;
            }
        }

        return result;
    }

    @Override
    public Map<String, Object> defineModel(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        result.put("login", login);
        result.put("warn", warn);

        return result;
    }
}
