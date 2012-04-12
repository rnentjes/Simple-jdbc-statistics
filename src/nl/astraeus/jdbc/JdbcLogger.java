package nl.astraeus.jdbc;

import nl.astraeus.jdbc.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
        public QueryType type;
        public String sql;
        public long milli;
        public long nano;
        public int count;
        public int hash;

        public LogEntry(int hash, QueryType type, String sql, long milli, long nano) {
            this.hash = hash;
            this.type = type;
            this.sql = sql;
            this.milli = milli;
            this.nano = nano;
            this.count = 1;
        }

        public void addCount(long milli, long nano) {
            synchronized (this) {
                count++;
                this.milli += milli;
                this.nano += nano;
            }
        }

        public int getCount() {
            return count;
        }

        public String getMilli() {
            return Util.formatNano(milli*1000000/count);
        }

        public String getNano() {
            return Util.formatNano(nano/count);
        }

        public String getTotal() {
            return Util.formatNano(nano);
        }

        public String getSql() {
            return sql;
        }
    }

    private Map<Integer, LogEntry> queries;

    public JdbcLogger() {
        queries = new ConcurrentHashMap<Integer, LogEntry>();
    }

    public void logEntry(QueryType type, String sql, long milli, long nano) {
        int hash = sql.hashCode();
        LogEntry entry = queries.get(hash);

        if (entry == null) {
            entry = new LogEntry(hash, type, sql, milli, nano);
            queries.put(hash, entry);
        } else {
            entry.addCount(milli, nano);
        }

        if (queries.size() > 1000) {
            List<Integer> toRemove = new LinkedList<Integer>();
            List<LogEntry> values = new LinkedList<LogEntry>(queries.values());

            Collections.sort(values, new Comparator<LogEntry>() {
                @Override
                public int compare(LogEntry o1, LogEntry o2) {
                    return o1.count - o2.count;
                }
            });

            while (queries.size() > 900) {
                queries.remove(values.remove(0).hash);

            }
        }
    }

    public static void log(QueryType type, String sql, long milli, long nano) {
        instance.logEntry(type, sql, milli, nano);
    }

    public Collection<LogEntry> getEntries() {
        return queries.values();
    }

}
