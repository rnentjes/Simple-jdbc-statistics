package nl.astraeus.jdbc.web.model;

import nl.astraeus.jdbc.JdbcLogger;
import nl.astraeus.jdbc.SqlFormatter;
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
    public long totalNanoTime;
    public long queryNanoTime;

    public long timestamp;

    public TransactionEntry(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public List<JdbcLogger.LogEntry> getQueries() {
        return queries;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getCount() {
        return queries.size();
    }

    public long getTotalTime() {
        return totalNanoTime;
    }

    public long getQueryTime() {
        return queryNanoTime;
    }

    public long getAvgTime() {
        return queryNanoTime / getCount();
    }

    public String getFormattedAvgTime() {
        return Formatting.formatNanoDuration(getAvgTime());
    }

    public String getFormattedTotalTime() {
        return Formatting.formatNanoDuration(getTotalTime());
    }

    public String getFormattedQueryTime() {
        return Formatting.formatNanoDuration(getQueryTime());
    }

    public String getFormattedTimestamp() {
        return Formatting.formatTimestamp(timestamp);
    }

    public String getFormattedEndTimestamp() {
        return Formatting.formatTimestamp(timestamp + (totalNanoTime / 1000000L));
    }

    public String getSql() {
        SqlFormatter formatter = new SqlFormatter();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransactionEntry that = (TransactionEntry) o;

        if (!queries.equals(that.queries)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return queries.hashCode();
    }
}
