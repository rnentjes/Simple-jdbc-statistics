package nl.astraeus.jdbc.web.page;

import nl.astraeus.http.SimpleWebServer;
import nl.astraeus.jdbc.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * User: rnentjes
 * Date: 4/12/12
 * Time: 9:16 PM
 */
public class ServerInfo extends TemplatePage {
    private static Logger logger = LoggerFactory.getLogger(ServerInfo.class);

    @Override
    public Page processRequest(HttpServletRequest request) {
        Page result = this;

        if ("select".equals(request.getParameter("action"))) {
            result = new ThreadStack(this, request.getParameter("actionValue"));
        }

        return result;
    }

    @Override
    public Map<String, Object> defineModel(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        SimpleWebServer server = Driver.getServer();

        result.put("threads", server.getThreads());

        return result;
    }
}
