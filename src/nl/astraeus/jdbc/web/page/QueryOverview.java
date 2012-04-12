package nl.astraeus.jdbc.web.page;

import nl.astraeus.jdbc.JdbcLogger;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * User: rnentjes
 * Date: 4/12/12
 * Time: 9:16 PM
 */
public class QueryOverview extends TemplatePage {

    boolean sortTotalCalls = true;
    boolean sortAvgTime = false;
    boolean sortTotalTime = false;

    @Override
    public Page processRequest(HttpServletRequest request) {
        if ("sortTotalCalls".equals(request.getParameter("action"))) {
            sortTotalCalls = true;
            sortAvgTime = false;
            sortTotalTime = false;
        } else if ("sortAvgTime".equals(request.getParameter("action"))) {
            sortTotalCalls = false;
            sortAvgTime = true;
            sortTotalTime = false;
        } else if ("sortTotalTime".equals(request.getParameter("action"))) {
            sortTotalCalls = false;
            sortAvgTime = false;
            sortTotalTime = true;
        }

        return this;
    }

    @Override
    public Map<String, Object> defineModel(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        List<JdbcLogger.LogEntry> entries = new ArrayList<JdbcLogger.LogEntry>(JdbcLogger.get().getEntries());

        if (sortTotalCalls) {
            Collections.sort(entries, new Comparator<JdbcLogger.LogEntry>() {
                @Override
                public int compare(JdbcLogger.LogEntry o1, JdbcLogger.LogEntry o2) {
                    return o2.count - o1.count;
                }
            });
        } else if (sortAvgTime) {
            Collections.sort(entries, new Comparator<JdbcLogger.LogEntry>() {
                @Override
                public int compare(JdbcLogger.LogEntry o1, JdbcLogger.LogEntry o2) {
                    long n1 = o1.nano / o1.count;
                    long n2 = o2.nano / o2.count;

                    if (n2 > n1) {
                        return 1;
                    } else if (n2 < n1) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        } else if (sortTotalTime) {
            Collections.sort(entries, new Comparator<JdbcLogger.LogEntry>() {
                @Override
                public int compare(JdbcLogger.LogEntry o1, JdbcLogger.LogEntry o2) {
                    if (o2.nano > o1.nano) {
                        return 1;
                    } else if (o2.nano < o1.nano) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        }

        result.put("queries", entries);
        result.put("count", entries.size());

        result.put("sortTotalCalls", sortTotalCalls);
        result.put("sortAvgTime", sortAvgTime);
        result.put("sortTotalTime", sortTotalTime);

        return result;
    }

}
