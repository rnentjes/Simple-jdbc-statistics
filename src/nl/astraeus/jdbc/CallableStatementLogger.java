package nl.astraeus.jdbc;

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
    public void registerOutParameter(int parameterIndex, int sqlType) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void registerOutParameter(int parameterIndex, int sqlType, int scale) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean wasNull() throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getString(int parameterIndex) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean getBoolean(int parameterIndex) throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public byte getByte(int parameterIndex) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public short getShort(int parameterIndex) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getInt(int parameterIndex) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public long getLong(int parameterIndex) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public float getFloat(int parameterIndex) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public double getDouble(int parameterIndex) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public BigDecimal getBigDecimal(int parameterIndex, int scale) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public byte[] getBytes(int parameterIndex) throws SQLException {
        return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Date getDate(int parameterIndex) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Time getTime(int parameterIndex) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Timestamp getTimestamp(int parameterIndex) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object getObject(int parameterIndex) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public BigDecimal getBigDecimal(int parameterIndex) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object getObject(int parameterIndex, Map<String, Class<?>> map) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Ref getRef(int parameterIndex) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Blob getBlob(int parameterIndex) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Clob getClob(int parameterIndex) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Array getArray(int parameterIndex) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Date getDate(int parameterIndex, Calendar cal) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Time getTime(int parameterIndex, Calendar cal) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Timestamp getTimestamp(int parameterIndex, Calendar cal) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void registerOutParameter(int parameterIndex, int sqlType, String typeName) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void registerOutParameter(String parameterName, int sqlType) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void registerOutParameter(String parameterName, int sqlType, int scale) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void registerOutParameter(String parameterName, int sqlType, String typeName) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public URL getURL(int parameterIndex) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setURL(String parameterName, URL val) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setNull(String parameterName, int sqlType) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setBoolean(String parameterName, boolean x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setByte(String parameterName, byte x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setShort(String parameterName, short x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setInt(String parameterName, int x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setLong(String parameterName, long x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setFloat(String parameterName, float x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setDouble(String parameterName, double x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setBigDecimal(String parameterName, BigDecimal x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setString(String parameterName, String x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setBytes(String parameterName, byte[] x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setDate(String parameterName, Date x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setTime(String parameterName, Time x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setTimestamp(String parameterName, Timestamp x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setAsciiStream(String parameterName, InputStream x, int length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setBinaryStream(String parameterName, InputStream x, int length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setObject(String parameterName, Object x, int targetSqlType, int scale) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setObject(String parameterName, Object x, int targetSqlType) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setObject(String parameterName, Object x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setCharacterStream(String parameterName, Reader reader, int length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setDate(String parameterName, Date x, Calendar cal) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setTime(String parameterName, Time x, Calendar cal) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setTimestamp(String parameterName, Timestamp x, Calendar cal) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setNull(String parameterName, int sqlType, String typeName) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getString(String parameterName) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean getBoolean(String parameterName) throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public byte getByte(String parameterName) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public short getShort(String parameterName) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getInt(String parameterName) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public long getLong(String parameterName) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public float getFloat(String parameterName) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public double getDouble(String parameterName) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public byte[] getBytes(String parameterName) throws SQLException {
        return new byte[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Date getDate(String parameterName) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Time getTime(String parameterName) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Timestamp getTimestamp(String parameterName) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object getObject(String parameterName) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public BigDecimal getBigDecimal(String parameterName) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object getObject(String parameterName, Map<String, Class<?>> map) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Ref getRef(String parameterName) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Blob getBlob(String parameterName) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Clob getClob(String parameterName) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Array getArray(String parameterName) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Date getDate(String parameterName, Calendar cal) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Time getTime(String parameterName, Calendar cal) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Timestamp getTimestamp(String parameterName, Calendar cal) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public URL getURL(String parameterName) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public RowId getRowId(int parameterIndex) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public RowId getRowId(String parameterName) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setRowId(String parameterName, RowId x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setNString(String parameterName, String value) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setNCharacterStream(String parameterName, Reader value, long length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setNClob(String parameterName, NClob value) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setClob(String parameterName, Reader reader, long length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setBlob(String parameterName, InputStream inputStream, long length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setNClob(String parameterName, Reader reader, long length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public NClob getNClob(int parameterIndex) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public NClob getNClob(String parameterName) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setSQLXML(String parameterName, SQLXML xmlObject) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public SQLXML getSQLXML(int parameterIndex) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public SQLXML getSQLXML(String parameterName) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getNString(int parameterIndex) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getNString(String parameterName) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Reader getNCharacterStream(int parameterIndex) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Reader getNCharacterStream(String parameterName) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Reader getCharacterStream(int parameterIndex) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Reader getCharacterStream(String parameterName) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setBlob(String parameterName, Blob x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setClob(String parameterName, Clob x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setAsciiStream(String parameterName, InputStream x, long length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setBinaryStream(String parameterName, InputStream x, long length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setCharacterStream(String parameterName, Reader reader, long length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setAsciiStream(String parameterName, InputStream x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setBinaryStream(String parameterName, InputStream x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setCharacterStream(String parameterName, Reader reader) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setNCharacterStream(String parameterName, Reader value) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setClob(String parameterName, Reader reader) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setBlob(String parameterName, InputStream inputStream) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setNClob(String parameterName, Reader reader) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public <T> T getObject(int parameterIndex, Class<T> type) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public <T> T getObject(String parameterName, Class<T> type) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ResultSet executeQuery() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int executeUpdate() throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setNull(int parameterIndex, int sqlType) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setBoolean(int parameterIndex, boolean x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setByte(int parameterIndex, byte x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setShort(int parameterIndex, short x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setInt(int parameterIndex, int x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setLong(int parameterIndex, long x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setFloat(int parameterIndex, float x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setDouble(int parameterIndex, double x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setString(int parameterIndex, String x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setBytes(int parameterIndex, byte[] x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setDate(int parameterIndex, Date x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setTime(int parameterIndex, Time x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void clearParameters() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setObject(int parameterIndex, Object x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean execute() throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addBatch() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setRef(int parameterIndex, Ref x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setBlob(int parameterIndex, Blob x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setClob(int parameterIndex, Clob x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setArray(int parameterIndex, Array x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public ResultSetMetaData getMetaData() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setURL(int parameterIndex, URL x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public ParameterMetaData getParameterMetaData() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setRowId(int parameterIndex, RowId x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setNString(int parameterIndex, String value) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setNClob(int parameterIndex, NClob value) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setClob(int parameterIndex, Reader reader) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setNClob(int parameterIndex, Reader reader) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int executeUpdate(String sql) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void close() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getMaxFieldSize() throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setMaxFieldSize(int max) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getMaxRows() throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setMaxRows(int max) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setEscapeProcessing(boolean enable) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getQueryTimeout() throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setQueryTimeout(int seconds) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void cancel() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public SQLWarning getWarnings() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void clearWarnings() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setCursorName(String name) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean execute(String sql) throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ResultSet getResultSet() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getUpdateCount() throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean getMoreResults() throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setFetchDirection(int direction) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getFetchDirection() throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setFetchSize(int rows) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getFetchSize() throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getResultSetConcurrency() throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getResultSetType() throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addBatch(String sql) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void clearBatch() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int[] executeBatch() throws SQLException {
        return new int[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Connection getConnection() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean getMoreResults(int current) throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public ResultSet getGeneratedKeys() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int executeUpdate(String sql, String[] columnNames) throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean execute(String sql, int[] columnIndexes) throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean execute(String sql, String[] columnNames) throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getResultSetHoldability() throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isClosed() throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setPoolable(boolean poolable) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isPoolable() throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void closeOnCompletion() throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isCloseOnCompletion() throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
