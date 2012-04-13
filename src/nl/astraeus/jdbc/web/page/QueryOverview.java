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

        List<JdbcLogger.LogEntry> entries = JdbcLogger.get().getEntries();
        Map<Integer, Integer> duplicateCount = new HashMap<Integer, Integer>();

        long fromTime = System.currentTimeMillis();
        long toTime = System.currentTimeMillis();
        long avgTime = 0;

        if (!entries.isEmpty()) {
            fromTime = entries.get(0).getTimeStamp();
            toTime = entries.get(entries.size()-1).getTimeStamp();

            long total = 0;

            for (JdbcLogger.LogEntry le : entries) {
                total += le.getNano();

                Integer count = duplicateCount.get(le.getHash());

                if (count == null) {
                    count = 1;
                } else {
                    count++;
                }

                duplicateCount.put(le.getHash(), count);
                le.setCount(count);
            }

            avgTime = total / entries.size();
        }

        if (sortTotalCalls) {
            Collections.sort(entries, new Comparator<JdbcLogger.LogEntry>() {
                @Override
                public int compare(JdbcLogger.LogEntry o1, JdbcLogger.LogEntry o2) {
                    return o2.getCount() - o1.getCount();
                }
            });
        } else if (sortAvgTime) {
            Collections.sort(entries, new Comparator<JdbcLogger.LogEntry>() {
                @Override
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
            Collections.sort(entries, new Comparator<JdbcLogger.LogEntry>() {
                @Override
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

        result.put("queries", entries);
        result.put("count", entries.size());

        result.put("sortTotalCalls", sortTotalCalls);
        result.put("sortAvgTime", sortAvgTime);
        result.put("sortTotalTime", sortTotalTime);

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        DateFormat dateFormatter = new SimpleDateFormat("HH:ss:mm.SSS");

        result.put("fromTime", dateFormatter.format(new Date(fromTime)));
        result.put("toTime", dateFormatter.format(new Date(toTime)));
        result.put("deltaTime", dateFormatter.format(new Date(toTime-fromTime)));
        result.put("avgTime", Util.formatNano(avgTime));


        return result;
    }

}
