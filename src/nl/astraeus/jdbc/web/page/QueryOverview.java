package nl.astraeus.jdbc.web.page;

import nl.astraeus.jdbc.JdbcLogger;
import nl.astraeus.jdbc.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * User: rnentjes
 * Date: 4/12/12
 * Time: 9:16 PM
 */
public class QueryOverview extends StatsPage {
    private static Logger logger = LoggerFactory.getLogger(QueryOverview.class);

    public static enum Selected {
        TOTAL_TIME,
        AVERAGE,
        CALLS,
        LAST100
    }

    Selected selected = Selected.AVERAGE;

    public QueryOverview() {}

    public QueryOverview(String sorting) {
        if ("total".equals(sorting)) {
            selected = Selected.TOTAL_TIME;
        } else if ("average".equals(sorting)) {
            selected = Selected.AVERAGE;
        } else if ("calls".equals(sorting)) {
            selected = Selected.CALLS;
        } else {
            selected = Selected.LAST100;
        }
    }

    @Override
    public void get() {
        set();
    }

    @Override
    public void post() {
        if ("Clear queries".equals(getParameter("action"))) {
            JdbcLogger.get(getServerInfo().port).clear();
        }

        set();
    }

    public void set() {
        List<JdbcLogger.LogEntry> list;

        long fromTime = System.currentTimeMillis();
        long toTime = System.currentTimeMillis();
        long avgTime = 0;

        if (selected == Selected.LAST100) {
            list = JdbcLogger.get(getServerInfo().port).getLast100();

            if (!list.isEmpty()) {
                fromTime = list.get(0).getMilli();
                toTime = list.get(list.size()-1).getMilli();
                avgTime = ((toTime - fromTime) / list.size()) * 1000000L;
            }

            set("count", list.size());
        } else {
            List<JdbcLogger.LogEntry> entries = JdbcLogger.get(getServerInfo().port).getEntries();

            Map<Integer, JdbcLogger.LogEntry> condensed = new HashMap<Integer, JdbcLogger.LogEntry>();

            if (!entries.isEmpty()) {
                fromTime = entries.get(0).getTimestamp();
                toTime = entries.get(entries.size() - 1).getTimestamp();

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

            if (selected == Selected.CALLS) {
                Collections.sort(list, new Comparator<JdbcLogger.LogEntry>() {
                    public int compare(JdbcLogger.LogEntry o1, JdbcLogger.LogEntry o2) {
                        return o2.getCount() - o1.getCount();
                    }
                });
            } else if (selected == Selected.AVERAGE) {
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
            } else if (selected == Selected.TOTAL_TIME) {
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
            set("count", entries.size());
        }

        set("queries", list);

        set("sortTotalCalls", selected == Selected.CALLS);
        set("sortAvgTime", selected == Selected.AVERAGE);
        set("sortTotalTime", selected == Selected.TOTAL_TIME);
        set("sortLast100", selected == Selected.LAST100);

        DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss.SSS");

        set("fromTime", dateFormatter.format(new Date(fromTime)));
        set("toTime", dateFormatter.format(new Date(toTime)));

        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        set("deltaTime", dateFormatter.format(new Date(toTime - fromTime)));
        set("avgTime", Util.formatNano(avgTime));
    }

}
