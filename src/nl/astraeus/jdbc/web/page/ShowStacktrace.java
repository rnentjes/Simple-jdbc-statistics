package nl.astraeus.jdbc.web.page;

import nl.astraeus.jdbc.JdbcLogger;
import nl.astraeus.jdbc.SqlFormatter;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    public static class TraceElement {
        private StackTraceElement element;
        private boolean highlight;

        public TraceElement(StackTraceElement element, boolean highlight) {
            this.element = element;
            this.highlight = highlight;
        }

        public StackTraceElement getElement() {
            return element;
        }

        public boolean getHighlight() {
            return highlight;
        }
    }

    @Override
    public Map<String, Object> defineModel(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        List<TraceElement> trace = new LinkedList<TraceElement>();

        for (int index = 4; index < logEntry.getStackTrace().length; index++) {
            boolean hl = logEntry.getStackTrace()[index].getClassName().startsWith("com.brandcleaner");
            trace.add(new TraceElement(logEntry.getStackTrace()[index], hl));
        }

        SqlFormatter formatter = new SqlFormatter();

        result.put("trace", trace);
        result.put("count", trace.size());
        result.put("sql", logEntry.getSql());

        result.put("sortAvgTime", sortAvgTime);
        result.put("sortTime", sortTime);

        return result;
    }

}
