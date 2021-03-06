package nl.astraeus.jdbc.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * User: rnentjes
 * Date: 4/12/12
 * Time: 7:32 PM
 */
public class JdbcStatisticsExample {


    public static void main(String [] args) throws Exception {
        Class.forName("org.h2.Driver");
        Class.forName("nl.astraeus.jdbc.Driver");

        new Thread() {
            @Override
            public void run() {
                try {
                    new JdbcStatisticsExample(18080, "TEST1");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new JdbcStatisticsExample(18081, "TEST2");
    }

    public JdbcStatisticsExample(int port, String dbname) throws Exception {
        // webServerConnections=1;numberOfQueries=2500;logStacktraces=true;formattedQueries=true
        Connection conn = DriverManager.getConnection("jdbc:stat:webServerPort="+port+":jdbc:h2:mem:"+dbname, "user", "password");
        conn.setAutoCommit(false);

        Statement statement = null;

        statement = conn.createStatement();
        statement.execute("CREATE TABLE "+dbname+" (ID INT PRIMARY KEY, NAME VARCHAR(255))");
        statement.close();

        conn.commit();

        boolean running = true;
        int count = 1;
        PreparedStatement ps = null;

        while (running) {
            String TableName = dbname+"_"+(System.nanoTime() % 1000);

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

            conn.commit();

            Thread.sleep(10);

            ps = conn.prepareStatement("SELECT * FROM "+TableName);
            ResultSet rs = ps.executeQuery();

            statement.close();

            ps = conn.prepareStatement("UPDATE "+TableName+" SET NAME = ?");

            ps.setString(1, "String  "+System.currentTimeMillis());

            ps.execute();
            ps.close();

            conn.commit();

            Thread.sleep(1234);
        }

        conn.close();
    }
}
