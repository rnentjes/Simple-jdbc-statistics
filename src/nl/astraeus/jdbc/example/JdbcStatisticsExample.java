package nl.astraeus.jdbc.example;

import java.sql.*;

/**
 * User: rnentjes
 * Date: 4/12/12
 * Time: 7:32 PM
 */
public class JdbcStatisticsExample {


    public static void main(String [] args) throws Exception {
        Class.forName("org.h2.Driver");
        Class.forName("nl.astraeus.jdbc.Driver");

        new JdbcStatisticsExample();
    }

    public JdbcStatisticsExample() throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:stat:jdbc:h2:mem:test", "user", "password");

        Statement statement = null;

        statement = conn.createStatement();
        statement.execute("CREATE TABLE TEST(ID INT PRIMARY KEY, NAME VARCHAR(255))");
        statement.close();

        conn.setAutoCommit(false);

        boolean running = true;
        int count = 1;
        PreparedStatement ps = null;

        while (running) {
            Thread.sleep(10);

            String TableName = "TEST"+(System.nanoTime() % 1000);

            try {
                ps = conn.prepareStatement("SELECT COUNT(*) FROM "+TableName);
                ResultSet rs = ps.executeQuery();
            } catch (SQLException e) {
                statement = conn.createStatement();
                statement.execute("CREATE TABLE "+TableName+" (ID INT PRIMARY KEY, NAME VARCHAR(255))");
                statement.close();
            } finally {
                if (ps!=null) {
                    ps.close();
                }
            }

            ps = conn.prepareStatement("INSERT INTO "+TableName+" VALUES (?, ?)");

            ps.setInt(1, count++);
            ps.setString(2, "String  "+System.currentTimeMillis());

            ps.execute();
            ps.close();

            Thread.sleep(10);

            conn.commit();
            ps = conn.prepareStatement("SELECT * FROM "+TableName);
            ResultSet rs = ps.executeQuery();

            statement.close();

            ps = conn.prepareStatement("UPDATE "+TableName+" SET NAME = ?");

            ps.setString(1, "String  "+System.currentTimeMillis());

            ps.execute();
            ps.close();

            conn.commit();
            Thread.sleep(10);
        }

        conn.close();
    }
}
