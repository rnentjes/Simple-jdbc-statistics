package nl.astraeus.jdbc;

import nl.astraeus.jdbc.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * User: riennentjes
 * Date: Jul 12, 2008
 * Time: 9:15:21 AM
 */
public class JdbcLogger {
    private final static Logger logger = LoggerFactory.getLogger(JdbcLogger.class);

    private final static JdbcLogger instance = new JdbcLogger();

    public static JdbcLogger get() {
        return instance;
    }

    public final static class LogEntry {
        private QueryType type;
        private String sql;
        private long timeStamp;
        private long milli;
        private long nano;
        private int hash;
        private int count;

        public LogEntry(int hash, QueryType type, String sql, long milli, long nano) {
            this.timeStamp = System.currentTimeMillis();
            this.hash = hash;
            this.type = type;
            this.sql = sql;
            this.milli = milli;
            this.nano = nano;
            this.count = 1;
        }

        public LogEntry(LogEntry le) {
            this.timeStamp = System.currentTimeMillis();
            this.hash = le.hash;
            this.type = le.type;
            this.sql = le .sql;
            this.milli = le.milli;
            this.nano = le.nano;
            this.count = le.count;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getHash() {
            return hash;
        }

        public long getTimeStamp() {
            return timeStamp;
        }

        public long getMilli() {
            return milli;
        }

        public long getNano() {
            return nano;
        }

        public String getFormattedMilli() {
            return Util.formatNano(milli*1000000/count);
        }

        public String getFormattedNano() {
            return Util.formatNano(nano/count);
        }

        public String getTotal() {
            return Util.formatNano(nano);
        }

        public String getSql() {
            return sql;
        }

        public void addCount(LogEntry le) {
            count++;
            this.milli += le.getMilli();
            this.nano += le.getNano();
            this.timeStamp = 0;
        }
    }

    private final List<LogEntry> queries;
    private long startTime;
    private int cacheSize;

    public JdbcLogger() {
        queries = new LinkedList<LogEntry>();
        startTime = System.currentTimeMillis();
        cacheSize = 2500;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public void setCacheSize(int cacheSize) {
        this.cacheSize = cacheSize;
    }

    public void logEntry(QueryType type, String sql, long milli, long nano) {
        int hash = sql.hashCode();

        LogEntry entry = new LogEntry(hash, type, sql, milli, nano);

        synchronized (queries) {
            queries.add(entry);

            while (queries.size() > cacheSize) {
                entry = queries.remove(0);
                startTime = entry.getMilli();
            }
        }
    }

    public static void log(QueryType type, String sql, long milli, long nano) {
        instance.logEntry(type, sql, milli, nano);
    }

    public List<LogEntry> getEntries() {
        synchronized (queries) {
            return new LinkedList<LogEntry>(queries);
        }
    }

}
