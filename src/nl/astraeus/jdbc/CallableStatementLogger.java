package nl.astraeus.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;

/**
 * User: rnentjes
 * Date: 4/17/12
 * Time: 9:48 PM
 */
public class CallableStatementLogger implements CallableStatement {
    private final static Logger log = LoggerFactory.getLogger(PreparedStatementLogger.class);

    private JdbcLogger logger;
    private CallableStatement statement;

    private String sql;
    private QueryType type;
    private boolean autocommit;
    private long milli;
    private long nano;

    public CallableStatementLogger(JdbcLogger logger, Connection connection, String sql) throws SQLException {
        this.logger = logger;
        this.sql = sql;
        this.type = QueryType.CALLABLE;
        clearTime();
        statement = connection.prepareCall(sql);
    }

    public CallableStatementLogger(JdbcLogger logger, Connection connection, String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
        this.logger = logger;
        this.sql = sql;
        this.type = QueryType.CALLABLE;
        clearTime();
        statement = connection.prepareCall(sql, resultSetType, resultSetConcurrency);
    }

    public CallableStatementLogger(JdbcLogger logger, Connection connection, String sql, int resultSetType, int resultSetConcurrency, int resultSetHoldability) throws SQLException {
        this.logger = logger;
        this.sql = sql;
        this.type = QueryType.CALLABLE;
        clearTime();
        statement = connection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }

    private void clearTime() {
        milli = System.currentTimeMillis();
        nano = System.nanoTime();
    }

    private void log(QueryType type, String sql) throws SQLException {
        long m = System.currentTimeMillis() - milli;
        long n = System.nanoTime() - nano;

        logger.logEntry(type, sql, m, n, autocommit);
    }

    public void registerOutParameter(int parameterIndex, int sqlType) throws SQLException {
        statement.registerOutParameter(parameterIndex, sqlType);
    }

    public void registerOutParameter(int parameterIndex, int sqlType, int scale) throws SQLException {
        statement.registerOutParameter(parameterIndex, sqlType, scale);
    }

    public boolean wasNull() throws SQLException {
        return statement.wasNull();
    }

    public String getString(int parameterIndex) throws SQLException {
        return statement.getString(parameterIndex);
    }

    public boolean getBoolean(int parameterIndex) throws SQLException {
        return statement.getBoolean(parameterIndex);
    }

    public byte getByte(int parameterIndex) throws SQLException {
        return statement.getByte(parameterIndex);
    }

    public short getShort(int parameterIndex) throws SQLException {
        return statement.getShort(parameterIndex);
    }

    public int getInt(int parameterIndex) throws SQLException {
        return statement.getInt(parameterIndex);
    }

    public long getLong(int parameterIndex) throws SQLException {
        return statement.getLong(parameterIndex);
    }

    public float getFloat(int parameterIndex) throws SQLException {
        return statement.getFloat(parameterIndex);
    }

    public double getDouble(int parameterIndex) throws SQLException {
        return statement.getDouble(parameterIndex);
    }

    public BigDecimal getBigDecimal(int parameterIndex, int scale) throws SQLException {
        return statement.getBigDecimal(parameterIndex, scale);
    }

    public byte[] getBytes(int parameterIndex) throws SQLException {
        return statement.getBytes(parameterIndex);
    }

    public Date getDate(int parameterIndex) throws SQLException {
        return statement.getDate(parameterIndex);
    }

    public Time getTime(int parameterIndex) throws SQLException {
        return statement.getTime(parameterIndex);
    }

    public Timestamp getTimestamp(int parameterIndex) throws SQLException {
        return statement.getTimestamp(parameterIndex);
    }

    public Object getObject(int parameterIndex) throws SQLException {
        return statement.getObject(parameterIndex);
    }

    public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
        return statement.getBigDecimal(parameterIndex);
    }

    public Object getObject(int parameterIndex, Map<String, Class<?>> map) throws SQLException {
        return statement.getObject(parameterIndex, map);
    }

    public Ref getRef(int parameterIndex) throws SQLException {
        return statement.getRef(parameterIndex);
    }

    public Blob getBlob(int parameterIndex) throws SQLException {
        return statement.getBlob(parameterIndex);
    }

    public Clob getClob(int parameterIndex) throws SQLException {
        return statement.getClob(parameterIndex);
    }

    public Array getArray(int parameterIndex) throws SQLException {
        return statement.getArray(parameterIndex);
    }

    public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
        return statement.getDate(parameterIndex, cal);
    }

    public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
        return statement.getTime(parameterIndex, cal);
    }

    public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws SQLException {
        return statement.getTimestamp(parameterIndex, cal);
    }

    public void registerOutParameter(int parameterIndex, int sqlType, String typeName) throws SQLException {
        statement.registerOutParameter(parameterIndex, sqlType, typeName);
    }

    public void registerOutParameter(String parameterName, int sqlType) throws SQLException {
        statement.registerOutParameter(parameterName, sqlType);
    }

    public void registerOutParameter(String parameterName, int sqlType, int scale) throws SQLException {
        statement.registerOutParameter(parameterName, sqlType, scale);
    }

    public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException {
        statement.registerOutParameter(parameterName, sqlType, typeName);
    }

    public URL getURL(int parameterIndex) throws SQLException {
        return statement.getURL(parameterIndex);
    }

    public void setURL(String parameterName, URL val) throws SQLException {
        statement.setURL(parameterName, val);
    }

    public void setNull(String parameterName, int sqlType) throws SQLException {
        statement.setNull(parameterName, sqlType);
    }

    public void setBoolean(String parameterName, boolean x) throws SQLException {
        statement.setBoolean(parameterName, x);
    }

    public void setByte(String parameterName, byte x) throws SQLException {
        statement.setByte(parameterName, x);
    }

    public void setShort(String parameterName, short x) throws SQLException {
        statement.setShort(parameterName, x);
    }

    public void setInt(String parameterName, int x) throws SQLException {
        statement.setInt(parameterName, x);
    }

    public void setLong(String parameterName, long x) throws SQLException {
        statement.setLong(parameterName, x);
    }

    public void setFloat(String parameterName, float x) throws SQLException {
        statement.setFloat(parameterName, x);
    }

    public void setDouble(String parameterName, double x) throws SQLException {
        statement.setDouble(parameterName, x);
    }

    public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
        statement.setBigDecimal(parameterName, x);
    }

    public void setString(String parameterName, String x) throws SQLException {
        statement.setString(parameterName, x);
    }

    public void setBytes(String parameterName, byte[] x) throws SQLException {
        statement.setBytes(parameterName, x);
    }

    public void setDate(String parameterName, Date x) throws SQLException {
        statement.setDate(parameterName, x);
    }

    public void setTime(String parameterName, Time x) throws SQLException {
        statement.setTime(parameterName, x);
    }

    public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
        statement.setTimestamp(parameterName, x);
    }

    public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
        statement.setAsciiStream(parameterName, x, length);
    }

    public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
        statement.setBinaryStream(parameterName, x, length);
    }

    public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws SQLException {
        statement.setObject(parameterName, x, targetSqlType, scale);
    }

    public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
        statement.setObject(parameterName, x, targetSqlType);
    }

    public void setObject(String parameterName, Object x) throws SQLException {
        statement.setObject(parameterName, x);
    }

    public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
        statement.setCharacterStream(parameterName, reader, length);
    }

    public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
        statement.setDate(parameterName, x, cal);
    }

    public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
        statement.setTime(parameterName, x, cal);
    }

    public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
        statement.setTimestamp(parameterName, x, cal);
    }

    public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
        statement.setNull(parameterName, sqlType, typeName);
    }

    public String getString(String parameterName) throws SQLException {
        return statement.getString(parameterName);
    }

    public boolean getBoolean(String parameterName) throws SQLException {
        return statement.getBoolean(parameterName);
    }

    public byte getByte(String parameterName) throws SQLException {
        return statement.getByte(parameterName);
    }

    public short getShort(String parameterName) throws SQLException {
        return statement.getShort(parameterName);
    }

    public int getInt(String parameterName) throws SQLException {
        return statement.getShort(parameterName);
    }

    public long getLong(String parameterName) throws SQLException {
        return statement.getLong(parameterName);
    }

    public float getFloat(String parameterName) throws SQLException {
        return statement.getFloat(parameterName);
    }

    public double getDouble(String parameterName) throws SQLException {
        return statement.getDouble(parameterName);
    }

    public byte[] getBytes(String parameterName) throws SQLException {
        return statement.getBytes(parameterName);
    }

    public Date getDate(String parameterName) throws SQLException {
        return statement.getDate(parameterName);
    }

    public Time getTime(String parameterName) throws SQLException {
        return statement.getTime(parameterName);
    }

    public Timestamp getTimestamp(String parameterName) throws SQLException {
        return statement.getTimestamp(parameterName);
    }

    public Object getObject(String parameterName) throws SQLException {
        return statement.getObject(parameterName);
    }

    public BigDecimal getBigDecimal(String parameterName) throws SQLException {
        return statement.getBigDecimal(parameterName);
    }

    public Object getObject(String parameterName, Map<String, Class<?>> map) throws SQLException {
        return statement.getObject(parameterName, map);
    }

    public Ref getRef(String parameterName) throws SQLException {
        return statement.getRef(parameterName);
    }

    public Blob getBlob(String parameterName) throws SQLException {
        return statement.getBlob(parameterName);
    }

    public Clob getClob(String parameterName) throws SQLException {
        return statement.getClob(parameterName);
    }

    public Array getArray(String parameterName) throws SQLException {
        return statement.getArray(parameterName);
    }

    public Date getDate(String parameterName, Calendar cal) throws SQLException {
        return statement.getDate(parameterName, cal);
    }

    public Time getTime(String parameterName, Calendar cal) throws SQLException {
        return statement.getTime(parameterName, cal);
    }

    public Timestamp getTimestamp(String parameterName, Calendar cal) throws SQLException {
        return statement.getTimestamp(parameterName, cal);
    }

    public URL getURL(String parameterName) throws SQLException {
        return statement.getURL(parameterName);
    }

    public RowId getRowId(int parameterIndex) throws SQLException {
        return statement.getRowId(parameterIndex);
    }

    public RowId getRowId(String parameterName) throws SQLException {
        return statement.getRowId(parameterName);
    }

    public void setRowId(String parameterName, RowId x) throws SQLException {
        statement.setRowId(parameterName, x);
    }

    public void setNString(String parameterName, String value) throws SQLException {
        statement.setNString(parameterName, value);
    }

    public void setNCharacterStream(String parameterName, Reader value, long length) throws SQLException {
        statement.setNCharacterStream(parameterName, value, length);
    }

    public void setNClob(String parameterName, NClob value) throws SQLException {
        statement.setNClob(parameterName, value);
    }

    public void setClob(String parameterName, Reader reader, long length) throws SQLException {
        statement.setClob(parameterName, reader, length);
    }

    public void setBlob(String parameterName, InputStream inputStream, long length) throws SQLException {
        statement.setBlob(parameterName, inputStream, length);
    }

    public void setNClob(String parameterName, Reader reader, long length) throws SQLException {
        statement.setNClob(parameterName, reader, length);
    }

    public NClob getNClob(int parameterIndex) throws SQLException {
        return statement.getNClob(parameterIndex);
    }

    public NClob getNClob(String parameterName) throws SQLException {
        return statement.getNClob(parameterName);
    }

    public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException {
        statement.setSQLXML(parameterName, xmlObject);
    }

    public SQLXML getSQLXML(int parameterIndex) throws SQLException {
        return statement.getSQLXML(parameterIndex);
    }

    public SQLXML getSQLXML(String parameterName) throws SQLException {
        return statement.getSQLXML(parameterName);
    }

    public String getNString(int parameterIndex) throws SQLException {
        return statement.getNString(parameterIndex);
    }

    public String getNString(String parameterName) throws SQLException {
        return statement.getNString(parameterName);
    }

    public Reader getNCharacterStream(int parameterIndex) throws SQLException {
        return statement.getNCharacterStream(parameterIndex);
    }

    public Reader getNCharacterStream(String parameterName) throws SQLException {
        return statement.getNCharacterStream(parameterName);
    }

    public Reader getCharacterStream(int parameterIndex) throws SQLException {
        return statement.getCharacterStream(parameterIndex);
    }

    public Reader getCharacterStream(String parameterName) throws SQLException {
        return statement.getCharacterStream(parameterName);
    }

    public void setBlob(String parameterName, Blob x) throws SQLException {
        statement.setBlob(parameterName, x);
    }

    public void setClob(String parameterName, Clob x) throws SQLException {
        statement.setClob(parameterName, x);
    }

    public void setAsciiStream(String parameterName, InputStream x, long length) throws SQLException {
        statement.setAsciiStream(parameterName, x, length);
    }

    public void setBinaryStream(String parameterName, InputStream x, long length) throws SQLException {
        statement.setBinaryStream(parameterName, x, length);
    }

    public void setCharacterStream(String parameterName, Reader reader, long length) throws SQLException {
        statement.setCharacterStream(parameterName, reader, length);
    }

    public void setAsciiStream(String parameterName, InputStream x) throws SQLException {
        statement.setAsciiStream(parameterName, x);
    }

    public void setBinaryStream(String parameterName, InputStream x) throws SQLException {
        statement.setBinaryStream(parameterName, x);
    }

    public void setCharacterStream(String parameterName, Reader reader) throws SQLException {
        statement.setCharacterStream(parameterName, reader);
    }

    public void setNCharacterStream(String parameterName, Reader value) throws SQLException {
        statement.setNCharacterStream(parameterName, value);
    }

    public void setClob(String parameterName, Reader reader) throws SQLException {
        statement.setClob(parameterName, reader);
    }

    public void setBlob(String parameterName, InputStream inputStream) throws SQLException {
        statement.setBlob(parameterName, inputStream);
    }

    public void setNClob(String parameterName, Reader reader) throws SQLException {
        statement.setNClob(parameterName, reader);
    }

    public <T> T getObject(int parameterIndex, Class<T> type) throws SQLException {
        return statement.getObject(parameterIndex, type);
    }

    public <T> T getObject(String parameterName, Class<T> type) throws SQLException {
        return statement.getObject(parameterName, type);
    }

    public ResultSet executeQuery() throws SQLException {
        try {
            clearTime();
            return statement.executeQuery();
        } finally {
            log(QueryType.CALLABLE, sql);
        }
    }

    public int executeUpdate() throws SQLException {
        try {
            clearTime();
            return statement.executeUpdate();
        } finally {
            log(QueryType.CALLABLE, sql);
        }
    }

    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        statement.setNull(parameterIndex, sqlType);
    }

    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        statement.setBoolean(parameterIndex, x);
    }

    public void setByte(int parameterIndex, byte x) throws SQLException {
        statement.setByte(parameterIndex, x);
    }

    public void setShort(int parameterIndex, short x) throws SQLException {
        statement.setShort(parameterIndex, x);
    }

    public void setInt(int parameterIndex, int x) throws SQLException {
        statement.setInt(parameterIndex, x);
    }

    public void setLong(int parameterIndex, long x) throws SQLException {
        statement.setLong(parameterIndex, x);
    }

    public void setFloat(int parameterIndex, float x) throws SQLException {
        statement.setFloat(parameterIndex, x);
    }

    public void setDouble(int parameterIndex, double x) throws SQLException {
        statement.setDouble(parameterIndex, x);
    }

    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        statement.setBigDecimal(parameterIndex, x);
    }

    public void setString(int parameterIndex, String x) throws SQLException {
        statement.setString(parameterIndex, x);
    }

    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        statement.setBytes(parameterIndex, x);
    }

    public void setDate(int parameterIndex, Date x) throws SQLException {
        statement.setDate(parameterIndex, x);
    }

    public void setTime(int parameterIndex, Time x) throws SQLException {
        statement.setTime(parameterIndex, x);
    }

    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        statement.setTimestamp(parameterIndex, x);
    }

    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
        statement.setAsciiStream(parameterIndex, x, length);
    }

    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
        statement.setUnicodeStream(parameterIndex, x, length);
    }

    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
        statement.setBinaryStream(parameterIndex, x, length);
    }

    public void clearParameters() throws SQLException {
        statement.clearParameters();
    }

    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
        statement.setObject(parameterIndex, x, targetSqlType);
    }

    public void setObject(int parameterIndex, Object x) throws SQLException {
        statement.setObject(parameterIndex, x);
    }

    public boolean execute() throws SQLException {
        try {
            clearTime();
            return statement.execute();
        } finally {
            log(QueryType.CALLABLE, sql);
        }
    }

    public void addBatch() throws SQLException {
        statement.addBatch();
    }

    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        statement.setCharacterStream(parameterIndex, reader, length);
    }

    public void setRef(int parameterIndex, Ref x) throws SQLException {
        statement.setRef(parameterIndex, x);
    }

    public void setBlob(int parameterIndex, Blob x) throws SQLException {
        statement.setBlob(parameterIndex, x);
    }

    public void setClob(int parameterIndex, Clob x) throws SQLException {
        statement.setClob(parameterIndex, x);
    }

    public void setArray(int parameterIndex, Array x) throws SQLException {
        statement.setArray(parameterIndex, x);
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        return statement.getMetaData();
    }

    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        statement.setDate(parameterIndex, x, cal);
    }

    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        statement.setTime(parameterIndex, x, cal);
    }

    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        statement.setTimestamp(parameterIndex, x, cal);
    }

    public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
        statement.setNull(parameterIndex, sqlType, typeName);
    }

    public void setURL(int parameterIndex, URL x) throws SQLException {
        statement.setURL(parameterIndex, x);
    }

    public ParameterMetaData getParameterMetaData() throws SQLException {
        return statement.getParameterMetaData();
    }

    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        statement.setRowId(parameterIndex, x);
    }

    public void setNString(int parameterIndex, String value) throws SQLException {
        statement.setNString(parameterIndex, value);
    }

    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        statement.setNCharacterStream(parameterIndex, value, length);
    }

    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        statement.setNClob(parameterIndex, value);
    }

    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        statement.setClob(parameterIndex, reader, length);
    }

    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        statement.setBlob(parameterIndex, inputStream, length);
    }

    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        statement.setNClob(parameterIndex, reader, length);
    }

    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        statement.setSQLXML(parameterIndex, xmlObject);
    }

    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
        statement.setObject(parameterIndex, x, targetSqlType, scaleOrLength);
    }

    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        statement.setAsciiStream(parameterIndex, x, length);
    }

    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        statement.setBinaryStream(parameterIndex, x, length);
    }

    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        statement.setCharacterStream(parameterIndex, reader, length);
    }

    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        statement.setAsciiStream(parameterIndex, x);
    }

    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        statement.setBinaryStream(parameterIndex, x);
    }

    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        statement.setCharacterStream(parameterIndex, reader);
    }

    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
        statement.setNCharacterStream(parameterIndex, value);
    }

    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        statement.setClob(parameterIndex, reader);
    }

    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        statement.setBlob(parameterIndex, inputStream);
    }

    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        statement.setNClob(parameterIndex, reader);
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        try {
            clearTime();
            return statement.executeQuery(sql);
        } finally {
            log(QueryType.CALLABLE, sql);
        }
    }

    public int executeUpdate(String sql) throws SQLException {
        try {
            clearTime();
            return statement.executeUpdate(sql);
        } finally {
            log(QueryType.CALLABLE, sql);
        }
    }

    public void close() throws SQLException {
        clearTime();
        statement.close();
    }

    public int getMaxFieldSize() throws SQLException {
        return statement.getMaxFieldSize();
    }

    public void setMaxFieldSize(int max) throws SQLException {
        statement.setMaxFieldSize(max);
    }

    public int getMaxRows() throws SQLException {
        return statement.getMaxRows();
    }

    public void setMaxRows(int max) throws SQLException {
        statement.setMaxRows(max);
    }

    public void setEscapeProcessing(boolean enable) throws SQLException {
        statement.setEscapeProcessing(enable);
    }

    public int getQueryTimeout() throws SQLException {
        return statement.getQueryTimeout();
    }

    public void setQueryTimeout(int seconds) throws SQLException {
        statement.setQueryTimeout(seconds);
    }

    public void cancel() throws SQLException {
        statement.cancel();
    }

    public SQLWarning getWarnings() throws SQLException {
        return statement.getWarnings();
    }

    public void clearWarnings() throws SQLException {
        statement.clearWarnings();
    }

    public void setCursorName(String name) throws SQLException {
        statement.setCursorName(name);
    }

    public boolean execute(String sql) throws SQLException {
        try {
            clearTime();
            return statement.execute(sql);
        } finally {
            log(QueryType.CALLABLE, sql);
        }
    }

    public ResultSet getResultSet() throws SQLException {
        try {
            clearTime();
            return statement.getResultSet();
        } finally {
            log(QueryType.CALLABLE, sql);
        }
    }

    public int getUpdateCount() throws SQLException {
        return statement.getUpdateCount();
    }

    public boolean getMoreResults() throws SQLException {
        return statement.getMoreResults();
    }

    public void setFetchDirection(int direction) throws SQLException {
        statement.setFetchDirection(direction);
    }

    public int getFetchDirection() throws SQLException {
        return statement.getFetchDirection();
    }

    public void setFetchSize(int rows) throws SQLException {
        statement.setFetchSize(rows);
    }

    public int getFetchSize() throws SQLException {
        return statement.getFetchSize();
    }

    public int getResultSetConcurrency() throws SQLException {
        return statement.getResultSetConcurrency();
    }

    public int getResultSetType() throws SQLException {
        return statement.getResultSetType();
    }

    public void addBatch(String sql) throws SQLException {
        this.sql += "\n\n" + sql;
        statement.addBatch(sql);
    }

    public void clearBatch() throws SQLException {
        statement.clearBatch();
    }

    public int[] executeBatch() throws SQLException {
        try {
            clearTime();
            return statement.executeBatch();
        } finally {
            log(QueryType.CALLABLE, sql);
        }
    }

    public Connection getConnection() throws SQLException {
        return statement.getConnection();
    }

    public boolean getMoreResults(int current) throws SQLException {
        return statement.getMoreResults(current);
    }

    public ResultSet getGeneratedKeys() throws SQLException {
        return statement.getGeneratedKeys();
    }

    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        try {
            clearTime();
            return statement.executeUpdate(sql, autoGeneratedKeys);
        } finally {
            log(QueryType.CALLABLE, sql);
        }
    }

    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        try {
            clearTime();
            return statement.executeUpdate(sql, columnIndexes);
        } finally {
            log(QueryType.CALLABLE, sql);
        }
    }

    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        try {
            clearTime();
            return statement.executeUpdate(sql, columnNames);
        } finally {
            log(QueryType.CALLABLE, sql);
        }
    }

    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        try {
            clearTime();
            return statement.execute(sql, autoGeneratedKeys);
        } finally {
            log(QueryType.CALLABLE, sql);
        }
    }

    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        try {
            clearTime();
            return statement.execute(sql, columnIndexes);
        } finally {
            log(QueryType.CALLABLE, sql);
        }
    }

    public boolean execute(String sql, String[] columnNames) throws SQLException {
        try {
            clearTime();
            return statement.execute(sql, columnNames);
        } finally {
            log(QueryType.CALLABLE, sql);
        }
    }

    public int getResultSetHoldability() throws SQLException {
        return statement.getResultSetHoldability();
    }

    public boolean isClosed() throws SQLException {
        return statement.isClosed();
    }

    public void setPoolable(boolean poolable) throws SQLException {
        statement.setPoolable(poolable);
    }

    public boolean isPoolable() throws SQLException {
        return statement.isPoolable();
    }

    public void closeOnCompletion() throws SQLException {
        statement.closeOnCompletion();
    }

    public boolean isCloseOnCompletion() throws SQLException {
        return statement.isCloseOnCompletion();
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return statement.unwrap(iface);
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return statement.isWrapperFor(iface);
    }

}
