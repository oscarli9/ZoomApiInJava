package zoomapi.handlers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import zoomapi.OAuthClient;
import zoomapi.components.ChatChannels;
import zoomapi.models.Channel;
import zoomapi.models.Result;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChannelHandler extends SQLiteGenericTableHandler<Channel> {
    private ChatChannels chatChannels;

    public ChannelHandler(OAuthClient oAuthClient) {
        super(Channel.class);
        chatChannels = oAuthClient.getChatChannels();
    }

    public List<Channel> getChannels(String clientId) {
        ResultSet rs = get("clientId", clientId);
        List<Channel> channels = new ArrayList<>();
        try {
            long timeStamp = rs.getLong("timeStamp");
            if (!isValidCache(timeStamp)) {
                HashMap<String, String> map = new HashMap<>();
                map.put("timeStamp", String.valueOf(timeStamp));
                delete(map);
                Result result = chatChannels.list();
                JsonObject jsonObject = (JsonObject) result.getResult();
                List<Channel> updatedChannels = parseChannelsInfo(clientId, jsonObject);
                insertChannels(updatedChannels);
                return updatedChannels;
            } else {
                while (rs.next()) {
                    String channelId = rs.getString("channelId");
                    String channelName = rs.getString("channelName");
                    Integer type = rs.getInt("type");
                    Channel channel = new Channel(channelId, channelName, type);
                    channels.add(channel);
                }
            }
        } catch (SQLException e) {
            Result result = chatChannels.list();
            JsonObject jsonObject = (JsonObject) result.getResult();
            List<Channel> updatedChannels = parseChannelsInfo(clientId, jsonObject);
            insertChannels(updatedChannels);
            return updatedChannels;
        }
        return channels;
    }

    public void insertChannels(List<Channel> channels) {
        long timeStamp = System.currentTimeMillis();
        for (Channel channel : channels) {
            HashMap<String, String> map = new HashMap<>();
            map.put("clientId", channel.getClientId());
            map.put("channelId", channel.getChannelId());
            map.put("channelName", channel.getChannelName());
            map.put("type", String.valueOf(channel.getType()));
            map.put("timeStamp", String.valueOf(timeStamp));
            insert(map);
        }
    }

    public List<Channel> parseChannelsInfo(String clientId, JsonObject jsonObject) {
        List<Channel> channels = new ArrayList<>();
        JsonArray jsonArray = jsonObject.getAsJsonArray("channels");
        for (int i=0; i<jsonArray.size(); i++) {
            JsonElement jsonElement = jsonArray.get(i);
            String channelId = jsonElement.getAsJsonObject().get("id").getAsString();
            String channelName = jsonElement.getAsJsonObject().get("name").getAsString();
            Integer type = jsonElement.getAsJsonObject().get("type").getAsInt();
            Channel channel = new Channel(channelId, channelName, type);
            channel.setClientId(clientId);
            channels.add(channel);
        }
        return channels;
    }
}
