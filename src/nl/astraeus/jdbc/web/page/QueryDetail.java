package nl.astraeus.jdbc.web.page;

import nl.astraeus.jdbc.JdbcLogger;
import nl.astraeus.jdbc.util.Util;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: rnentjes
 * Date: 4/12/12
 * Time: 9:16 PM
 */
public class QueryDetail extends TemplatePage {

    private Page previous;
    private int hash;
    private String sql = null;

    boolean sortAvgTime = false;
    boolean sortTime = false;

    public QueryDetail(Page previous, int hash) {
        this.previous = previous;
        this.hash = hash;
    }

    @Override
    public Page processRequest(HttpServletRequest request) {
        Page result = this;

        if ("sortTime".equals(request.getParameter("action"))) {
            sortTime = true;
            sortAvgTime = false;
        } else if ("sortAvgTime".equals(request.getParameter("action"))) {
            sortTime = false;
            sortAvgTime = true;
        } else if ("stacktrace".equals(request.getParameter("action"))) {
            long timestamp = Long.parseLong(request.getParameter("actionValue"));
            JdbcLogger.LogEntry found = null;
            for (JdbcLogger.LogEntry entry : JdbcLogger.get().getEntries()) {
                if (entry.getHash() == hash && entry.getTimestamp() == timestamp) {
                    found = entry;
                    break;
                }
            }

            if (found != null) {
                result = new ShowStacktrace(this, found);
            } else {
                // warning ! found ....
            }

        } else if ("back".equals(request.getParameter("action"))) {
            result = previous;
        }

        return result;
    }

    @Override
    public Map<String, Object> defineModel(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        List<JdbcLogger.LogEntry> entries = JdbcLogger.get().getEntries();

        long fromTime = System.currentTimeMillis();
        long toTime = 0;
        long avgTime = 0;

        List<JdbcLogger.LogEntry> list;
        list = new LinkedList<JdbcLogger.LogEntry>();

        if (!entries.isEmpty()) {
            long total = 0;
            for (JdbcLogger.LogEntry le : entries) {
                if (hash == le.getHash()) {
                    if (sql == null) {
                        sql = le.getSql();
                    }

                    list.add(le);
                    total += le.getMilli();
                    fromTime = Math.min(fromTime, le.getTimestamp());
                    toTime = Math.max(toTime, le.getTimestamp());
                }
            }

            avgTime = total / entries.size();
        }


        if (sortAvgTime) {
            Collections.sort(list, new Comparator<JdbcLogger.LogEntry>() {
                public int compare(JdbcLogger.LogEntry o1, JdbcLogger.LogEntry o2) {
                    long n1 = o1.getNano();
                    long n2 = o2.getNano();

                    if (n2 > n1) {
                        return 1;
                    } else if (n2 < n1) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        } else if (sortTime) {
            Collections.sort(list, new Comparator<JdbcLogger.LogEntry>() {
                public int compare(JdbcLogger.LogEntry o1, JdbcLogger.LogEntry o2) {
                    if (o2.getTimestamp() > o1.getTimestamp()) {
                        return 1;
                    } else if (o2.getTimestamp() < o1.getTimestamp()) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        }

        result.put("queries", list);
        result.put("count", list.size());
        result.put("sql", sql);

        result.put("sortAvgTime", sortAvgTime);
        result.put("sortTime", sortTime);

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss.SSS");

        result.put("fromTime", dateFormatter.format(new Date(fromTime)));
        result.put("toTime", dateFormatter.format(new Date(toTime)));
        result.put("deltaTime", dateFormatter.format(new Date(toTime-fromTime)));
        result.put("avgTime", Util.formatNano(avgTime));

        return result;
    }

}
