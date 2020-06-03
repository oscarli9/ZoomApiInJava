package zoomapi.models;

public class Channel {
    private String clientId;
    private String channelId;  // The channel id
    private String channelName;
    private Integer type;
    private long timeStamp;

    public Channel() {
    }

    public Channel(String channelId, String channelName, Integer type) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.type = type;
    }

    public Channel(String clientId, String channelId, String channelName, Integer type, long timeStamp) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.type = type;
        this.timeStamp = timeStamp;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public void setId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
