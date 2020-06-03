package zoomapi.handlers;

import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class TableTest {
    protected  Connection c;

    @Before
    public void init() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:zoom_cache.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Test
    public void tableExistsTest() {
        try {
            Statement stmt = c.createStatement();
            //String sql1 = "INSERT INTO Credentials (channelId"
            String sql = "SELECT * from Credentials;";
            ResultSet rs = stmt.executeQuery(sql);
            stmt.close();
            c.commit();
            while (rs.next()) {
                System.out.println(rs.getString("channelId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
