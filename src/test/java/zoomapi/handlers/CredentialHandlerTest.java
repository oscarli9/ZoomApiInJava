package zoomapi.handlers;

import org.junit.Test;
import zoomapi.models.Result;

import java.sql.ResultSet;
import java.util.HashMap;

public class CredentialHandlerTest {
    CredentialHandler credentialHandler = new CredentialHandler();

    @Test
    public void createTableTest() {
        credentialHandler.createTable();
    }

    @Test
    public void insertTest() {
        HashMap<String, String> map = new HashMap<>();
        map.put("clientId", "wbhbd");
        map.put("oauthToken", "ejbwjk");
        credentialHandler.insert(map);
    }

    @Test
    public void getTest() {
        System.out.println(credentialHandler.getOauthToken("wbhbd"));
        ResultSet rs = credentialHandler.get();
        try {
            while (rs.next()) {
                Integer a = rs.getInt("id");
                String s = rs.getString("oauthToken");
                System.out.println(a + s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
