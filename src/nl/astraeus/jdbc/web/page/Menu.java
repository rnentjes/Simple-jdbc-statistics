package nl.astraeus.jdbc.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * User: rnentjes
 * Date: 3/28/12
 * Time: 9:07 PM
 */
public class Menu extends TemplatePage {

    @Override
    public Page processRequest(HttpServletRequest request) {
         return this;
    }

    @Override
    public Map<String, Object> defineModel(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        
        result.put("user", request.getSession().getAttribute("user"));

        return result;
    }
}
