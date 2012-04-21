package nl.astraeus.jdbc.web.model;

import nl.astraeus.jdbc.JdbcLogger;
import nl.astraeus.jdbc.util.Formatting;

import java.util.LinkedList;
import java.util.List;

/**
 * User: rnentjes
 * Date: 4/21/12
 * Time: 10:44 PM
 */
public class TransactionEntry {
    public int id;
    public List<JdbcLogger.LogEntry> queries = new LinkedList<JdbcLogger.LogEntry>();
    public long nanoTime;
    public long timestamp;

    public TransactionEntry(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

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

    public String getFormattedAvgTime() {
        return Formatting.formatNanoDuration(nanoTime / getCount());
    }

    public String getFormattedTotalTime() {
        return Formatting.formatNanoDuration(nanoTime);
    }

    public String getFormattedTimestamp() {
        return Formatting.formatTimestamp(timestamp);
    }

    public String getFormattedEndTimestamp() {
        return Formatting.formatTimestamp(timestamp + (nanoTime / 1000000L));
    }

    public String getSql() {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (JdbcLogger.LogEntry entry : queries) {
            if (first) {
                first = false;
            } else {
                result.append("\n\n");
            }

            result.append(entry.getSql());
        }

        return result.toString();
    }
}
