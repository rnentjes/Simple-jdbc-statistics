package nl.astraeus.jdbc.web.page;

import nl.astraeus.jdbc.JdbcLogger;
import nl.astraeus.jdbc.util.Util;
import nl.astraeus.jdbc.web.model.TransactionEntry;
import nl.astraeus.web.page.Message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: rnentjes
 * Date: 4/12/12
 * Time: 9:16 PM
 */
public class TransactionOverview extends StatsPage {

    private boolean sortTotalCalls = true;
    private boolean sortAvgTime = false;
    private boolean sortTotalTime = false;
    private boolean sortQueryTime = false;

    private List<TransactionEntry> transactions = new LinkedList<TransactionEntry>();

    @Override
    public void get() {
        if ("sortTotalCalls".equals(getParameter("action"))) {
            sortTotalCalls = true;
            sortAvgTime = false;
            sortTotalTime = false;
            sortQueryTime = false;
        } else if ("sortAvgTime".equals(getParameter("action"))) {
            sortTotalCalls = false;
            sortAvgTime = true;
            sortTotalTime = false;
            sortQueryTime = false;
        } else if ("sortTotalTime".equals(getParameter("action"))) {
            sortTotalCalls = false;
            sortAvgTime = false;
            sortTotalTime = true;
            sortQueryTime = false;
        } else if ("sortQueryTime".equals(getParameter("action"))) {
            sortTotalCalls = false;
            sortAvgTime = false;
            sortTotalTime = false;
            sortQueryTime = true;
        } else if ("clear".equals(getParameter("action"))) {
            JdbcLogger.get().clear();
        } else if ("select".equals(getParameter("action"))) {
            String id = getParameter("actionValue");

            TransactionEntry entry = findTransaction(Integer.parseInt(id));
            if (entry != null) {
                // redirect
                // result = new TransactionDetail(this, entry);
            } else {
                addMessage(Message.Type.ERROR, "Transaction not found!", "Transaction with id '"+id+"' could not be found.");
            }
        }

        set();
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

    public void set() {
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

        set("transactions", transactions);
        set("count", entries.size());

        set("sortTotalCalls", sortTotalCalls);
        set("sortAvgTime", sortAvgTime);
        set("sortTotalTime", sortTotalTime);
        set("sortQueryTime", sortQueryTime);

        DateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss.SSS");

        set("fromTime", dateFormatter.format(new Date(fromTime)));
        set("toTime", dateFormatter.format(new Date(toTime)));

        dateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        set("deltaTime", dateFormatter.format(new Date(toTime-fromTime)));
        set("avgTime", Util.formatNano(avgTime));
    }

}
