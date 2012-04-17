package nl.astraeus.jdbc.web.page;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * User: rnentjes
 * Date: 4/17/12
 * Time: 9:19 PM
 */
public class Settings extends TemplatePage {


    @Override
    public Page processRequest(HttpServletRequest request) {
        Page result = this;



        return result;
    }

    @Override
    public Map<String, Object> defineModel(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        return result;
    }
}
