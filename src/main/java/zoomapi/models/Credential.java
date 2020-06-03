package zoomapi.models;

public class Credential {
    private String clientId; // The client ID
    private String oauthToken;
    private long timeStamp;

    public Credential() {
    }

    public Credential(String clientId, String oauthToken, long timeStamp) {
        this.clientId = clientId;
        this.oauthToken = oauthToken;
        this.timeStamp = timeStamp;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getOauthToken() {
        return oauthToken;
    }

    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
