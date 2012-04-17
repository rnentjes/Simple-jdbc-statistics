package nl.astraeus.jdbc.web.page;

import nl.astraeus.jdbc.JdbcLogger;
import nl.astraeus.jdbc.util.Util;

import javax.servlet.http.HttpServletRequest;
import java.security.KeyStore;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: rnentjes
 * Date: 4/12/12
 * Time: 9:16 PM
 */
public class ShowStacktrace extends TemplatePage {

    private Page previous;
    private JdbcLogger.LogEntry logEntry;

    boolean sortAvgTime = false;
    boolean sortTime = false;

    public ShowStacktrace(Page previous, JdbcLogger.LogEntry logEntry) {
        this.previous = previous;
        this.logEntry = logEntry;
    }

    @Override
    public Page processRequest(HttpServletRequest request) {

        if ("sortTime".equals(request.getParameter("action"))) {
            sortTime = true;
            sortAvgTime = false;
        } else if ("sortAvgTime".equals(request.getParameter("action"))) {
            sortTime = false;
            sortAvgTime = true;
        } else if ("back".equals(request.getParameter("action"))) {
            return previous;
        }

        return this;
    }

    @Override
    public Map<String, Object> defineModel(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        List<StackTraceElement> trace = new LinkedList<StackTraceElement>();

        for (int index = 4; index < logEntry.getStackTrace().length; index++) {
            trace.add(logEntry.getStackTrace()[index]);
        }

        result.put("trace", trace);
        result.put("count", trace.size());
        result.put("sql", logEntry.getSql());

        result.put("sortAvgTime", sortAvgTime);
        result.put("sortTime", sortTime);

        return result;
    }

}
