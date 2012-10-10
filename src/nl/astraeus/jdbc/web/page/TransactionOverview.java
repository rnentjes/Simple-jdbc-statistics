package nl.astraeus.jdbc.web.page;

import nl.astraeus.jdbc.JdbcLogger;
import nl.astraeus.jdbc.util.Formatting;
import nl.astraeus.jdbc.util.Util;
import nl.astraeus.jdbc.web.model.TransactionEntry;

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

    private boolean sortTotalCalls = true;
    private boolean sortAvgTime = false;
    private boolean sortTotalTime = false;
    private boolean sortQueryTime = false;

    private List<TransactionEntry> transactions = new LinkedList<TransactionEntry>();

    @Override
    public Page processRequest(HttpServletRequest request) {
        Page result = this;
        
        if ("sortTotalCalls".equals(request.getParameter("action"))) {
            sortTotalCalls = true;
            sortAvgTime = false;
            sortTotalTime = false;
            sortQueryTime = false;
        } else if ("sortAvgTime".equals(request.getParameter("action"))) {
            sortTotalCalls = false;
            sortAvgTime = true;
            sortTotalTime = false;
            sortQueryTime = false;
        } else if ("sortTotalTime".equals(request.getParameter("action"))) {
            sortTotalCalls = false;
            sortAvgTime = false;
            sortTotalTime = true;
            sortQueryTime = false;
        } else if ("sortQueryTime".equals(request.getParameter("action"))) {
            sortTotalCalls = false;
            sortAvgTime = false;
            sortTotalTime = false;
            sortQueryTime = true;
        } else if ("clear".equals(request.getParameter("action"))) {
            JdbcLogger.get().clear();
        } else if ("select".equals(request.getParameter("action"))) {
            String id = request.getParameter("actionValue");

            TransactionEntry entry = findTransaction(Integer.parseInt(id));
            if (entry != null) {
                result = new TransactionDetail(this, entry);
            } else {
                Warnings.get(request).addMessage(Warnings.Message.Type.ERROR, "Transaction not found!", "Transaction with id '"+id+"' could not be found.");
            }
        }

        return result;
    }
    
    private TransactionEntry findTransaction(int id) {
        TransactionEntry result = null;
        
        for (TransactionEntry entry : transactions) {
            if (entry.getId() == id) {
                result = entry;
                break;
            }
        }
        
        return result;
    }

    @Override
    public Map<String, Object> defineModel(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        List<JdbcLogger.LogEntry> entries = JdbcLogger.get().getEntries();

        long fromTime = System.currentTimeMillis();
        long toTime = System.currentTimeMillis();
        long avgTime = 0;

        transactions = new LinkedList<TransactionEntry>();
        Map<Long,TransactionEntry> currentTransactions = new HashMap<Long, TransactionEntry>();

        if (!entries.isEmpty()) {
            fromTime = entries.get(0).getTimestamp();
            toTime = entries.get(entries.size()-1).getTimestamp();

            long total = 0;
            int id = 1;

            for (JdbcLogger.LogEntry le : entries) {
                total += le.getNano();

                TransactionEntry entry = currentTransactions.get(le.getThreadId());

                if (entry == null) {
                    entry = new TransactionEntry(id++);
                    entry.timestamp = le.getTimestamp();
                    entry.queryNanoTime = 0;
                    entry.totalNanoTime = le.getNanoTimeStamp() - le.getNano();
                    currentTransactions.put(le.getThreadId(), entry);
                }

                entry.queries.add(le);
                entry.queryNanoTime += le.getNano();

                if (le.isEndOfTransaction()) {
                    entry.totalNanoTime = le.getNanoTimeStamp() - entry.totalNanoTime;
                    if (entry.getCount() > 1) {
                        entry.totalNanoTime += le.getNano();
                    }
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
                    long n1 = o1.getAvgTime();
                    long n2 = o2.getAvgTime();

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
                    if (o2.getTotalTime() > o1.getTotalTime()) {
                        return 1;
                    } else if (o2.getTotalTime() < o1.getTotalTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        } else if (sortQueryTime) {
            Collections.sort(transactions, new Comparator<TransactionEntry>() {
                public int compare(TransactionEntry o1, TransactionEntry o2) {
                    if (o2.getQueryTime() > o1.getQueryTime()) {
                        return 1;
                    } else if (o2.getQueryTime() < o1.getQueryTime()) {
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
        result.put("sortQueryTime", sortQueryTime);

        DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss.SSS");
        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        result.put("fromTime", dateFormatter.format(new Date(fromTime)));
        result.put("toTime", dateFormatter.format(new Date(toTime)));
        result.put("deltaTime", dateFormatter.format(new Date(toTime-fromTime)));
        result.put("avgTime", Util.formatNano(avgTime));

        return result;
    }

}
