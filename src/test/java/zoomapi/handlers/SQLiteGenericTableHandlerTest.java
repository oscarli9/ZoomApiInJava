package zoomapi.handlers;

import org.junit.Test;
import zoomapi.models.Channel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class SQLiteGenericTableHandlerTest {

    Channel channel = new Channel();
    SQLiteGenericTableHandler<Channel> sqLiteGenericTableHandler = new SQLiteGenericTableHandler<>(Channel.class);

    @Test
    public void createTable() {
        sqLiteGenericTableHandler.createTable();
    }

    @Test
    public void i1() {
        HashMap<String, String> map = new HashMap<>();
        map.put("channelId", "dwwd45-ev3gwg");
        map.put("channelName", "haha");
        map.put("type", "1");
        sqLiteGenericTableHandler.insert(map);
    }

    @Test
    public void i2() {
        HashMap<String, String> whereMap = new HashMap<>();
        whereMap.put("channelId", "dwwd45-ev3gwg");
        whereMap.put("channelName", "haha");

        HashMap<String, String> map = new HashMap<>();
        map.put("type", "2");
        map.put("channelName", "ha");
        sqLiteGenericTableHandler.update(whereMap, map);
    }

    @Test
    public void i3() {
        HashMap<String, String> whereMap = new HashMap<>();
        whereMap.put("channelId", "dwwd45-ev3gwg");
        //whereMap.put("channelName", "haha");

        sqLiteGenericTableHandler.delete(whereMap);
    }

    @Test
    public void i4() {
        HashMap<String, String> whereMap = new HashMap<>();
        whereMap.put("channelId", "dwwd45-ev3gwg");
        //whereMap.put("channelName", "haha");

        ResultSet rs = sqLiteGenericTableHandler.get();

        try {

            while (rs.next()) {
                System.out.println(rs.getString("channelId"));
                System.out.println(rs.getString("channelName"));
                System.out.println(rs.getInt("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
