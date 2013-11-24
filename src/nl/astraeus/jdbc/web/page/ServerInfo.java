package nl.astraeus.jdbc.web.page;

import nl.astraeus.http.SimpleWebServer;
import nl.astraeus.jdbc.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: rnentjes
 * Date: 4/12/12
 * Time: 9:16 PM
 */
public class ServerInfo extends StatsPage {
    private static Logger logger = LoggerFactory.getLogger(ServerInfo.class);

    @Override
    public void get() {
        if ("select".equals(getParameter("action"))) {
            //result = new ThreadStack(this, request.getParameter("actionValue"));
        }
    }

    public void set() {
        SimpleWebServer server = Driver.getServer();

        set("threads", server.getThreads());
   }
}
