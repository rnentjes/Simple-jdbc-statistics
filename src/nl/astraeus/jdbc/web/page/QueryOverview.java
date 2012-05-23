package nl.astraeus.jdbc.web.page;

import nl.astraeus.jdbc.JdbcLogger;
import nl.astraeus.jdbc.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: rnentjes
 * Date: 4/12/12
 * Time: 9:16 PM
 */
public class QueryOverview extends TemplatePage {
    private static Logger logger = LoggerFactory.getLogger(QueryOverview.class);

    boolean sortTotalCalls = true;
    boolean sortAvgTime = false;
    boolean sortTotalTime = false;

    @Override
    public Page processGetRequest(String action, String value) {
        Page result = this;

        if ("select".equals(action)) {
            return new QueryDetail(this, Integer.parseInt(value));
        }

        return result;
    }

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
        } else if ("record".equals(request.getParameter("action"))) {
            JdbcLogger.get().switchRecording();
        } else if ("clear".equals(request.getParameter("action"))) {
            JdbcLogger.get().clear();
        } else if ("select".equals(request.getParameter("action"))) {
            String hash = request.getParameter("actionValue");

            return new QueryDetail(this, Integer.parseInt(hash));
        }

        return this;
    }

    @Override
    public Map<String, Object> defineModel(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        List<JdbcLogger.LogEntry> entries = JdbcLogger.get().getEntries();

        long fromTime = System.currentTimeMillis();
        long toTime = System.currentTimeMillis();
        long avgTime = 0;

        Map<Integer, JdbcLogger.LogEntry> condensed = new HashMap<Integer, JdbcLogger.LogEntry>();
        List<JdbcLogger.LogEntry> list;

        if (!entries.isEmpty()) {
            fromTime = entries.get(0).getTimestamp();
            toTime = entries.get(entries.size()-1).getTimestamp();

            long total = 0;

            for (JdbcLogger.LogEntry le : entries) {
                total += le.getNano();

                JdbcLogger.LogEntry entry = condensed.get(le.getHash());

                if (entry == null) {
                    entry = new JdbcLogger.LogEntry(le);
                    condensed.put(entry.getHash(), entry);
                } else {
                    entry.addCount(le);
                }
            }

            avgTime = total / entries.size();
        }

        list = new LinkedList<JdbcLogger.LogEntry>(condensed.values());

        if (sortTotalCalls) {
            Collections.sort(list, new Comparator<JdbcLogger.LogEntry>() {
                public int compare(JdbcLogger.LogEntry o1, JdbcLogger.LogEntry o2) {
                    return o2.getCount() - o1.getCount();
                }
            });
        } else if (sortAvgTime) {
            Collections.sort(list, new Comparator<JdbcLogger.LogEntry>() {
                public int compare(JdbcLogger.LogEntry o1, JdbcLogger.LogEntry o2) {
                    long n1 = o1.getNano() / o1.getCount();
                    long n2 = o2.getNano() / o2.getCount();

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
            Collections.sort(list, new Comparator<JdbcLogger.LogEntry>() {
                public int compare(JdbcLogger.LogEntry o1, JdbcLogger.LogEntry o2) {
                    if (o2.getNano() > o1.getNano()) {
                        return 1;
                    } else if (o2.getNano() < o1.getNano()) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        }

        result.put("queries", list);
        result.put("count", entries.size());

        result.put("sortTotalCalls", sortTotalCalls);
        result.put("sortAvgTime", sortAvgTime);
        result.put("sortTotalTime", sortTotalTime);

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss.SSS");

        result.put("fromTime", dateFormatter.format(new Date(fromTime)));
        result.put("toTime", dateFormatter.format(new Date(toTime)));
        result.put("deltaTime", dateFormatter.format(new Date(toTime-fromTime)));
        result.put("avgTime", Util.formatNano(avgTime));

        result.put("recording", JdbcLogger.get().isRecording());

        return result;
    }
}
