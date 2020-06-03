package zoomapi.handlers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import zoomapi.OAuthClient;
import zoomapi.components.ChatMessages;
import zoomapi.models.Message;
import zoomapi.models.Result;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MessageHandler extends SQLiteGenericTableHandler<Message> {
    private ChatMessages chatMessages;

    public MessageHandler(OAuthClient oAuthClient) {
        super(Message.class);
        chatMessages = oAuthClient.getChatMessages();
    }

    public List<Message> getMessages(String userId, String channelId) {
        ResultSet rs = get("receiver", channelId);
        List<Message> messages = new ArrayList<>();
        try {
            long timeStamp = rs.getLong("timeStamp");
            if (!isValidCache(timeStamp)) {
                HashMap<String, String> map = new HashMap<>();
                map.put("timeStamp", String.valueOf(timeStamp));
                delete(map);
                List<Message> updatedMessages = getMessagesFromPast5Days(userId, channelId);
                insertMessages(updatedMessages);
                return updatedMessages;
            } else {
                while (rs.next()) {
                    String messageId = rs.getString("messageId");
                    String dateTime = rs.getString("dateTime");
                    String sender = rs.getString("sender");
                    String receiver = rs.getString("receiver");
                    int receiverFlag = rs.getInt("receiverIsChannel");
                    boolean receiverIsChannel = true;
                    if (receiverFlag == 0) receiverIsChannel = false;
                    String message = rs.getString("message");
                    Message newMessage = new Message(receiver, receiverIsChannel, message);
                    newMessage.setMessageId(messageId);
                    newMessage.setDateTime(dateTime);
                    newMessage.setSender(sender);
                    messages.add(newMessage);
                }
            }
        } catch (SQLException e) {
            List<Message> updatedMessages = getMessagesFromPast5Days(userId, channelId);
            insertMessages(updatedMessages);
            return updatedMessages;
        }
        return messages;
    }

    public void insertMessages(List<Message> messages) {
        long timeStamp = System.currentTimeMillis();
        for (Message message : messages) {
            HashMap<String, String> map = new HashMap<>();
            map.put("messageId", message.getMessageId());
            map.put("dateTime", message.getDateTime());
            map.put("sender", message.getSender());
            map.put("receiver", message.getReceiver());
            map.put("receiverIsChannel", message.isReceiverIsChannel()? "1" : "0");
            map.put("message", message.getMessage());
            map.put("timeStamp", String.valueOf(timeStamp));
            insert(map);
        }
    }

    public List<Message> getMessagesFromPast5Days(String userId, String channelId) {
        List<Message> messageList = new ArrayList<>();
        for (int i=0; i<=5; i++) {
            Calendar c = Calendar.getInstance();
            c.setTime(new Date());
            c.add(Calendar.DAY_OF_MONTH, -i);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String curDate = sdf.format(c.getTime());
            Result tempResult = chatMessages.list(userId, channelId, true, curDate);
            JsonObject jsonObject = (JsonObject) tempResult.getResult();
            JsonArray jsonArray = jsonObject.getAsJsonArray("messages");
            for (int j=0; j<jsonArray.size(); j++) {
                JsonObject tempObj = jsonArray.get(j).getAsJsonObject();
                String messageContent = tempObj.get("message").getAsString();
                //String sender = tempObj.get("sender").getAsString();
                String dateTime = tempObj.get("date_time").getAsString();
                String messageId = tempObj.get("id").getAsString();
                Message newMessage = new Message(channelId, true, messageContent);
                newMessage.setSender(userId);
                newMessage.setDateTime(dateTime);
                newMessage.setMessageId(messageId);
                newMessage.setReceiver(channelId);
                newMessage.setReceiverIsChannel(true);
                messageList.add(newMessage);
            }
        }
        return messageList;
    }
}
