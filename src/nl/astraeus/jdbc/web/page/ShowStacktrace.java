package nl.astraeus.jdbc.web.page;

import nl.astraeus.jdbc.JdbcLogger;
import nl.astraeus.jdbc.Parameter;
import nl.astraeus.jdbc.QueryType;
import nl.astraeus.jdbc.SqlFormatter;
import nl.astraeus.web.page.Message;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * User: rnentjes
 * Date: 4/12/12
 * Time: 9:16 PM
 */
public class ShowStacktrace extends StatsPage {

    private int hash;
    private JdbcLogger.LogEntry logEntry;

    boolean sortAvgTime = false;
    boolean sortTime = false;

    public ShowStacktrace(
            String hashStr,
            String timestamp
    ) {
        List<JdbcLogger.LogEntry> entries = JdbcLogger.get(getServerInfo().port).getEntries();

        hash = Integer.parseInt(hashStr);
        long tsValue = Long.parseLong(timestamp);

        for (JdbcLogger.LogEntry entry : entries) {
            if (entry.getHash() == hash && entry.getTimestamp() == tsValue) {
                this.logEntry = entry;
                break;
            }
        }

        if (logEntry == null) {
            addMessage(Message.Type.ERROR, "Error", "Stacktrace not found!");
            logEntry = new JdbcLogger.LogEntry(
                    -1,
                    QueryType.UNKNOWN,
                    "",
                    0,
                    0,
                    false,
                    true,
                    new HashSet<Parameter>()
            );
            logEntry.setStackTrace(new StackTraceElement[0]);
        }
    }

    @Override
    public void get() {

        if ("sortTime".equals(getParameter("action"))) {
            sortTime = true;
            sortAvgTime = false;
        } else if ("sortAvgTime".equals(getParameter("action"))) {
            sortTime = false;
            sortAvgTime = true;
        } else if ("back".equals(getParameter("action"))) {
            // go back (?)
        }

        set();
    }

    public static class TraceElement {
        private StackTraceElement element;
        private boolean highlight;

        public TraceElement(
                StackTraceElement element,
                boolean highlight
        ) {
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

    public void set() {
        List<TraceElement> trace = new LinkedList<TraceElement>();

        for (int index = 4; index < logEntry.getStackTrace().length; index++) {
            boolean hl = logEntry.getStackTrace()[index].getClassName().startsWith(getSettings().getPackageStart());
            trace.add(new TraceElement(logEntry.getStackTrace()[index], hl));
        }

        SqlFormatter formatter = new SqlFormatter();

        set("hash", hash);
        set("trace", trace);
        set("count", trace.size());
        set("sql", logEntry.getSql());

        set("sortAvgTime", sortAvgTime);
        set("sortTime", sortTime);
    }

}
