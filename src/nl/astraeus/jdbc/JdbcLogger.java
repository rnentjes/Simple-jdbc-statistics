package nl.astraeus.jdbc;

import nl.astraeus.jdbc.util.Util;
import nl.astraeus.jdbc.web.model.Settings;
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
        private long threadId;
        private QueryType type;
        private String sql;
        private long timeStamp;
        private long milli;
        private long nano;
        private int hash;
        private int count;
        private boolean autoCommit;
        private StackTraceElement[] stackTrace = null;

        public LogEntry(int hash, QueryType type, String sql, long milli, long nano, boolean isAutoCommit) {
            this.threadId = Thread.currentThread().getId();
            this.timeStamp = System.currentTimeMillis();
            this.hash = hash;
            this.type = type;
            this.sql = sql;
            this.milli = milli;
            this.nano = nano;
            this.autoCommit = isAutoCommit;
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
            this.autoCommit = le.autoCommit;
        }

        public QueryType getType() {
            return type;
        }

        public long getThreadId() {
            return threadId;
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

        public long getTimestamp() {
            return timeStamp;
        }

        public String getFormattedTimestamp() {
            return Util.formatTimestamp(timeStamp);
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
            if (Settings.get().isFormattedQueries() && QueryType.PREPARED == type) {
                return SqlFormatter.getHTMLFormattedSQL(sql);
            } else {
                return sql;
            }
        }

        public boolean isEndOfTransaction() {
            return sql.toLowerCase().trim().equals("commit")
                    || sql.toLowerCase().trim().equals("rollback")
                    || isAutoCommit();
        }

        public void addCount(LogEntry le) {
            count++;
            this.milli += le.getMilli();
            this.nano += le.getNano();
            this.timeStamp = 0;
        }

        public boolean hasStackTrace() {
            return stackTrace != null;
        }

        public void setStackTrace(StackTraceElement[] stackTrace) {
            this.stackTrace = stackTrace;
        }

        public StackTraceElement[] getStackTrace() {
            return stackTrace;
        }

        public boolean isAutoCommit() {
            return autoCommit;
        }
    }

    private final List<LogEntry> queries;
    private long startTime;
    private boolean recording = false;

    public JdbcLogger() {
        queries = new LinkedList<LogEntry>();
        startTime = System.currentTimeMillis();
    }

    public boolean isRecording() {
        return recording;
    }

    public void switchRecording() {
        recording = !recording;
    }

    public void clear() {
        queries.clear();
    }

    public void logEntry(QueryType type, String sql, long milli, long nano, boolean isAutoCommit) {
        int hash = sql.hashCode();

        LogEntry entry = new LogEntry(hash, type, sql, milli, nano, isAutoCommit);

        if (recording) {
            try {
                throw new IllegalStateException();
            } catch (IllegalStateException e) {
                entry.setStackTrace(e.getStackTrace());
            }
        }

        synchronized (queries) {
            queries.add(entry);

            while (queries.size() > Settings.get().getNumberOfQueries()) {
                entry = queries.remove(0);
                startTime = entry.getMilli();
            }
        }
    }

    public static void log(QueryType type, String sql, long milli, long nano, boolean isAutoCommit) {
        instance.logEntry(type, sql, milli, nano, isAutoCommit);
    }

    public List<LogEntry> getEntries() {
        synchronized (queries) {
            return new LinkedList<LogEntry>(queries);
        }
    }

}
