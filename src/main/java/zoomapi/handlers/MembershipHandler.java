package zoomapi.handlers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import zoomapi.OAuthClient;
import zoomapi.components.ChatChannels;
import zoomapi.models.Member;
import zoomapi.models.Result;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MembershipHandler extends SQLiteGenericTableHandler<Member> {
    private ChatChannels chatChannels;

    public MembershipHandler(OAuthClient oAuthClient) {
        super(Member.class);
        chatChannels = oAuthClient.getChatChannels();
    }

    public List<Member> getMembers(String channelId) {
        ResultSet rs = get("channelId", channelId);
        List<Member> members = new ArrayList<>();
        try {
            long timeStamp = rs.getLong("timeStamp");
            if (!isValidCache(timeStamp)) {
                HashMap<String, String> map = new HashMap<>();
                map.put("timeStamp", String.valueOf(timeStamp));
                delete(map);
                Result result = chatChannels.listChannelMembers(channelId);
                JsonObject jsonObject = (JsonObject) result.getResult();
                List<Member> updatedMembers = parseMembersInfo(channelId, jsonObject);
                insertMembers(updatedMembers);
                return updatedMembers;
            } else {
                while (rs.next()) {
                    String memberId = rs.getString("memberId");
                    String email = rs.getString("email");
                    String name = rs.getString("name");
                    String role = rs.getString("role");
                    Member member = new Member(email);
                    member.setMemberId(memberId);
                    member.setChannelId(channelId);
                    member.setName(name);
                    member.setRole(role);
                    members.add(member);
                }
            }
        } catch (SQLException e) {
            Result result = chatChannels.listChannelMembers(channelId);
            JsonObject jsonObject = (JsonObject) result.getResult();
            List<Member> updatedMembers = parseMembersInfo(channelId, jsonObject);
            insertMembers(updatedMembers);
            return updatedMembers;
        }
        return members;
    }

    public void insertMembers(List<Member> members) {
        long timeStamp = System.currentTimeMillis();
        for (Member member : members) {
            HashMap<String, String> map = new HashMap<>();
            map.put("channelId", member.getChannelId());
            map.put("channelName", member.getChannelName());
            map.put("memberId", member.getMemberId());
            map.put("email", member.getEmail());
            map.put("name", member.getName());
            map.put("role", member.getRole());
            map.put("timeStamp", String.valueOf(timeStamp));
            insert(map);
        }
    }

    public List<Member> parseMembersInfo(String channelId, JsonObject jsonObject) {
        List<Member> members = new ArrayList<>();
        JsonArray jsonArray = jsonObject.getAsJsonArray("members");
        for (int i=0; i<jsonArray.size(); i++) {
            JsonElement jsonElement = jsonArray.get(i);
            String memberId = jsonElement.getAsJsonObject().get("id").getAsString();
            String email = jsonElement.getAsJsonObject().get("email").getAsString();
            String name = jsonElement.getAsJsonObject().get("name").getAsString();
            String role = jsonElement.getAsJsonObject().get("role").getAsString();
            Member member = new Member(email);
            member.setMemberId(memberId);
            member.setChannelName(null);
            member.setChannelId(channelId);
            member.setName(name);
            member.setRole(role);
            members.add(member);
        }
        return members;
    }
}
