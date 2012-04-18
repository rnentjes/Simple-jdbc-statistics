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
public class TransactionOverview extends TemplatePage {

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
        } else if ("record".equals(request.getParameter("action"))) {
            JdbcLogger.get().switchRecording();
        } else if ("clear".equals(request.getParameter("action"))) {
            JdbcLogger.get().clear();
//        } else if ("select".equals(request.getParameter("action"))) {
//            String hash = request.getParameter("actionValue");
//
//            return new QueryDetail(this, Integer.parseInt(hash));
        }

        return this;
    }

    public static class TransactionEntry {
        public List<JdbcLogger.LogEntry> queries = new LinkedList<JdbcLogger.LogEntry>();
        public long nanoTime;
        public long timestamp;

        public long getTimestamp() {
            return timestamp;
        }

        public int getCount() {
            return queries.size();
        }

        public long getTotalTime() {
            return nanoTime;
        }

        public long getAvgTime() {
            return nanoTime / getCount();
        }

        public String getSql() {
            StringBuilder result = new StringBuilder();

            for (JdbcLogger.LogEntry entry : queries) {
                result.append(entry.getSql());
                result.append("\n\n");
            }

            return result.toString();
        }
    }

    @Override
    public Map<String, Object> defineModel(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        List<JdbcLogger.LogEntry> entries = JdbcLogger.get().getEntries();

        long fromTime = System.currentTimeMillis();
        long toTime = System.currentTimeMillis();
        long avgTime = 0;

        List<TransactionEntry> transactions = new LinkedList<TransactionEntry>();
        Map<Long,TransactionEntry> currentTransactions = new HashMap<Long, TransactionEntry>();

        if (!entries.isEmpty()) {
            fromTime = entries.get(0).getTimestamp();
            toTime = entries.get(entries.size()-1).getTimestamp();

            long total = 0;

            for (JdbcLogger.LogEntry le : entries) {
                total += le.getNano();

                TransactionEntry entry = currentTransactions.get(le.getThreadId());

                if (entry == null) {
                    entry = new TransactionEntry();
                    entry.timestamp = le.getTimestamp();
                    entry.nanoTime = 0;
                    currentTransactions.put(le.getThreadId(), entry);
                }

                entry.queries.add(le);
                entry.nanoTime += le.getNano();

                if (le.getSql().equals("commit") || le.getSql().equals("rollback") || le.isAutoCommit()) {
                    transactions.add(entry);
                    currentTransactions.remove(le.getThreadId());
                }
            }

            avgTime = total / entries.size();
        }

        if (sortTotalCalls) {
            Collections.sort(transactions, new Comparator<TransactionEntry>() {
                public int compare(TransactionEntry o1, TransactionEntry o2) {
                    return o2.queries.size() - o1.queries.size();
                }
            });
        } else if (sortAvgTime) {
            Collections.sort(transactions, new Comparator<TransactionEntry>() {
                public int compare(TransactionEntry o1, TransactionEntry o2) {
                    long n1 = o1.nanoTime / o1.queries.size();
                    long n2 = o2.nanoTime / o2.queries.size();

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
            Collections.sort(transactions, new Comparator<TransactionEntry>() {
                public int compare(TransactionEntry o1, TransactionEntry o2) {
                    if (o2.nanoTime > o1.nanoTime) {
                        return 1;
                    } else if (o2.nanoTime < o1.nanoTime) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        }

        result.put("transactions", transactions);
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
