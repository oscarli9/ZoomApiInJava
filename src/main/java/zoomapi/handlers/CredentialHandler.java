package zoomapi.handlers;

import zoomapi.models.Credential;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class CredentialHandler extends SQLiteGenericTableHandler<Credential> {
    public CredentialHandler() {
        super(Credential.class);
    }

    public String getOauthToken(String clientId) {
        ResultSet rs = get("clientId", clientId);
        String token;
        try {
            token = rs.getString("oauthToken");
            long timeStamp = rs.getLong("timeStamp");
            if (!isValidCache(timeStamp)) {
                HashMap<String, String> map = new HashMap<>();
                map.put("timeStamp", String.valueOf(timeStamp));
                delete(map);
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
        return token;
    }
}
