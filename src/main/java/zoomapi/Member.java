package zoomapi;

public class Member {
    private String channelId;
    private String channelName;
    private String id;
    private String email;
    private String name;
    private String role;

    public Member(String email) {
        this.email = email;
    }

    public Member setChannelId(String channelId) {
        this.channelId = channelId;
        return this;
    }

    public String getChannelId() {
        return channelId;
    }

    public Member setChannelName(String channelName) {
        this.channelName = channelName;
        return this;
    }

    public String getChannelName() {
        return channelName;
    }

    public Member setId(String id) {
        this.id = id;
        return this;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Member setName(String name) {
        this.name = name;
        return this;
    }

    public String getName() {
        return name;
    }

    public Member setRole(String role) {
        this.role = role;
        return this;
    }

    public String getRole() {
        return role;
    }
}
