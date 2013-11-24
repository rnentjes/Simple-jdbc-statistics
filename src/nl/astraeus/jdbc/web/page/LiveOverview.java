package nl.astraeus.jdbc.web.page;

import nl.astraeus.jdbc.JdbcLogger;
import nl.astraeus.jdbc.util.Util;
import nl.astraeus.web.page.Page;
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
public class LiveOverview extends StatsPage {
    private static Logger logger = LoggerFactory.getLogger(LiveOverview.class);

    boolean sortTotalCalls = true;
    boolean sortAvgTime = false;
    boolean sortTotalTime = false;

    @Override
    public void get() {
        Page result = this;

//        if ("select".equals(action)) {
//            return new QueryDetail(this, Integer.parseInt(value));
//        }

//        return result;
    }

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
        } else if ("clear".equals(request.getParameter("action"))) {
            JdbcLogger.get().clear();
        }

        return this;
    }

    public void set() {
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

        set("queries", list);
        set("count", entries.size());

        set("sortTotalCalls", sortTotalCalls);
        set("sortAvgTime", sortAvgTime);
        set("sortTotalTime", sortTotalTime);

        DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss.SSS");

        set("fromTime", dateFormatter.format(new Date(fromTime)));
        set("toTime", dateFormatter.format(new Date(toTime)));

        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        set("deltaTime", dateFormatter.format(new Date(toTime-fromTime)));
        set("avgTime", Util.formatNano(avgTime));

        set("test", getTest());
    }

    public String getTest() {
        return "Test HaHa";
    }
}
