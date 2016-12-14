package nl.astraeus.jdbc;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import nl.astraeus.jdbc.util.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: riennentjes
 * Date: Jul 12, 2008
 * Time: 9:15:21 AM
 */
public class JdbcLogger {
    private final static Logger logger = LoggerFactory.getLogger(JdbcLogger.class);

    private final static Map<Integer, JdbcLogger> instances = new ConcurrentHashMap<Integer, JdbcLogger>();

    public static JdbcLogger get(int port) {
        JdbcLogger result = instances.get(port);

        if (result == null) {
            result = new JdbcLogger(port);
            instances.put(port, result);
        }

        return result;
    }

    public final static class LogEntry {
        private long threadId;
        private QueryType type;
        private String sql;
        private long timeStamp;
        private long nanoTimeStamp;
        private long milli;
        private long nano;
        private int hash;
        private int count;
        private boolean autoCommit;
        private boolean formattedQueries;
        private StackTraceElement[] stackTrace = null;

        public LogEntry(int hash, QueryType type, String sql, long milli, long nano, boolean isAutoCommit, boolean formattedQueries) {
            this.threadId = Thread.currentThread().getId();
            this.timeStamp = System.currentTimeMillis();
            this.nanoTimeStamp = System.nanoTime();
            this.hash = hash;
            this.type = type;
            this.sql = sql;
            this.milli = milli;
            this.nano = nano;
            this.autoCommit = isAutoCommit;
            this.count = 1;
            this.formattedQueries = formattedQueries;
        }

        public LogEntry(LogEntry le) {
            this.timeStamp = System.currentTimeMillis();
            this.nanoTimeStamp = System.nanoTime();
            this.hash = le.hash;
            this.type = le.type;
            this.sql = le .sql;
            this.milli = le.milli;
            this.nano = le.nano;
            this.count = le.count;
            this.autoCommit = le.autoCommit;
            this.formattedQueries = le.formattedQueries;
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

        public long getNanoTimeStamp() {
            return nanoTimeStamp;
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
            if (formattedQueries) {
                return SqlFormatter.getHTMLFormattedSQL(sql);
            } else {
                return sql;
            }
        }

        public boolean isEndOfTransaction() {
            return sql.toLowerCase().trim().equals("commit")
                    || sql.toLowerCase().trim().equals("rollback")
                    || sql.toLowerCase().trim().equals("close")
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
    private final List<LogEntry> last100;
    private long startTime;
    private int port;

    public JdbcLogger(int port) {
        this.port = port;
        queries = new LinkedList<LogEntry>();
        last100 = new LinkedList<LogEntry>();
        startTime = System.currentTimeMillis();
    }

    public void clear() {
        queries.clear();
        last100.clear();
    }

    public void logEntry(QueryType type, String sql, long milli, long nano, boolean isAutoCommit) {
        int hash = sql.hashCode();
        Driver.StatsLogger logger = Driver.get(port);

        LogEntry entry = new LogEntry(hash, type, sql, milli, nano, isAutoCommit, logger.getSettings().isFormattedQueries());

        if (logger.getSettings().isRecordingStacktraces()) {
            try {
                throw new IllegalStateException();
            } catch (IllegalStateException e) {
                entry.setStackTrace(e.getStackTrace());
            }
        }

        synchronized (queries) {
            queries.add(entry);
            last100.add(entry);

            while (queries.size() > logger.getSettings().getNumberOfQueries()) {
                entry = queries.remove(0);
                startTime = entry.getMilli();
            }
            while (last100.size() > 100) {
                last100.remove(0);
            }
        }
    }

    public List<LogEntry> getEntries() {
        synchronized (queries) {
            return new LinkedList<LogEntry>(queries);
        }
    }

    public List<LogEntry> getLast100() {
        synchronized (queries) {
            return new LinkedList<LogEntry>(last100);
        }
    }
}
