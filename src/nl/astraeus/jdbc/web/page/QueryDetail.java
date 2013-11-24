package nl.astraeus.jdbc.web.page;

import nl.astraeus.jdbc.JdbcLogger;
import nl.astraeus.jdbc.SqlFormatter;
import nl.astraeus.jdbc.util.Util;
import nl.astraeus.jdbc.web.JdbcStatsMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: rnentjes
 * Date: 4/12/12
 * Time: 9:16 PM
 */
public class QueryDetail extends StatsPage {

    private int hash;
    private String sql = null;

    boolean sortAvgTime = false;
    boolean sortTime = false;

    public QueryDetail(String hash) {
        this.hash = Integer.parseInt(hash);
    }

    @Override
    public void get() {
        if ("sortTime".equals(getParameter("action"))) {
            sortTime = true;
            sortAvgTime = false;
        } else if ("sortAvgTime".equals(getParameter("action"))) {
            sortTime = false;
            sortAvgTime = true;
        } else if ("stacktrace".equals(getParameter("action"))) {
            long timestamp = Long.parseLong(getParameter("actionValue"));
            JdbcLogger.LogEntry found = null;
            for (JdbcLogger.LogEntry entry : JdbcLogger.get().getEntries()) {
                if (entry.getHash() == hash && entry.getTimestamp() == timestamp) {
                    found = entry;
                    break;
                }
            }

            if (found != null) {
                redirect(JdbcStatsMapping.JVM, Integer.toString(hash), Long.toString(timestamp));
                //result = new ShowStacktrace(this, found);
            } else {
                // warning ! found ....
            }

        } else if ("back".equals(getParameter("action"))) {
            redirect(JdbcStatsMapping.QUERIES);
        }

        set();
        //return result;
    }

    public void set() {
        List<JdbcLogger.LogEntry> entries = JdbcLogger.get().getEntries();

        long fromTime = System.currentTimeMillis();
        long toTime = 0;
        long avgTime = 0;

        List<JdbcLogger.LogEntry> list;
        list = new LinkedList<JdbcLogger.LogEntry>();
        SqlFormatter formatter = new SqlFormatter();

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

        set("queries", list);
        set("count", list.size());
        set("sql", sql);

        set("sortAvgTime", sortAvgTime);
        set("sortTime", sortTime);

        DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss.SSS");

        set("fromTime", dateFormatter.format(new Date(fromTime)));
        set("toTime", dateFormatter.format(new Date(toTime)));

        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        set("deltaTime", dateFormatter.format(new Date(toTime-fromTime)));
        set("avgTime", Util.formatNano(avgTime));
        set("hash", hash);
    }

}
