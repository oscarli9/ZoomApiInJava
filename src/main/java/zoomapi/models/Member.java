package zoomapi.models;

public class Member {
    private String channelId;
    private String channelName;
    private String memberId;
    private String email;
    private String name;
    private String role;
    private long timeStamp;

    public Member() {}

    public Member(String email) {
        this.email = email;
    }

    public Member(String channelId, String channelName, String memberId, String email, String name, String role, long timeStamp) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.memberId = memberId;
        this.email = email;
        this.name = name;
        this.role = role;
        this.timeStamp = timeStamp;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
