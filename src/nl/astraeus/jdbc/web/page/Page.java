package nl.astraeus.jdbc.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * User: rnentjes
 * Date: 3/28/12
 * Time: 3:20 PM
 */
public abstract class Page {

    public Page processGetRequest(String action, String value) {
        return this;
    }

    public abstract Page processRequest(HttpServletRequest request);
    public abstract Map<String, Object> defineModel(HttpServletRequest request);
    public abstract String render(HttpServletRequest request);

}
