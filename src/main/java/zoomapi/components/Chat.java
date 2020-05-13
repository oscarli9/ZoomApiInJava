package zoomapi.components;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import zoomapi.Message;
import zoomapi.Result;
import zoomapi.handlers.ISearchHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Chat extends BaseComponent {
    private final ChatChannels chatChannels;
    private final ChatMessages chatMessages;

    /**
     * Setup a new API Client
     * @param baseUrl : The base URI to the API
     * @param timeout : The timeout to use for requests
     * @param config  : The config details
     */
    public Chat(String baseUrl, int timeout, HashMap<String, String> config) {
        super(baseUrl, timeout, config);
        chatChannels = new ChatChannels(baseUrl, timeout, config);
        chatMessages = new ChatMessages(baseUrl, timeout, config);
    }

    public static ISearchHandler SEARCH_BY_SENDER = (ChatMessages chatMessages, String channelId, String fromDate, String toDate, String condition) -> {
        Result result = new Result();
        List<Message> messageList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date firstDate = sdf.parse(fromDate);
            Date secondDate = sdf.parse(toDate);
            long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            if (diff > 5) {
                result.setErrorMessage(
                        Result.TIME_RANGE_ERROR,
                        "No more than 5 days between fromDate and toDate."
                );
                return result;
            }
            else {
                for (int i=0; i<=diff; i++) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(firstDate);
                    c.add(Calendar.DAY_OF_MONTH, i);
                    String curDate = sdf.format(c.getTime());
                    result = chatMessages.list("me", channelId, true, curDate);
                    JsonObject jsonObject = (JsonObject) result.getResult();
                    JsonArray jsonArray = jsonObject.getAsJsonArray("messages");
                    for (int j=0; j<jsonArray.size(); j++) {
                        jsonObject = jsonArray.get(j).getAsJsonObject();
                        if (jsonObject.get("sender").getAsString().contains(condition)) {
                            String messageContent = jsonObject.get("message").getAsString();
                            String sender = jsonObject.get("sender").getAsString();
                            String dateTime = jsonObject.get("date_time").getAsString();
                            String messageId = jsonObject.get("id").getAsString();
                            Message newMessage = new Message(channelId, true, messageContent);
                            newMessage.setSender(sender);
                            newMessage.setDateTime(dateTime);
                            newMessage.setMessageId(messageId);
                            messageList.add(newMessage);
                        }
                    }
                }
                result.setResult(messageList);
            }
        } catch (ParseException e) {
            result.setErrorMessage(Result.PARSE_ERROR, "Error parsing the from date or the to date.");
            return result;
        }
        return result;
    };

    public static ISearchHandler SEARCH_BY_MESSAGE = (ChatMessages chatMessages, String channelId, String fromDate, String toDate, String condition) -> {
        Result result = new Result();
        List<Message> messageList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date firstDate = sdf.parse(fromDate);
            Date secondDate = sdf.parse(toDate);
            long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
            long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            if (diff > 5) {
                result.setErrorMessage(
                        Result.TIME_RANGE_ERROR,
                        "No more than 5 days between fromDate and toDate."
                );
                return result;
            }
            else {
                for (int i=0; i<=diff; i++) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(firstDate);
                    c.add(Calendar.DAY_OF_MONTH, i);
                    String curDate = sdf.format(c.getTime());
                    result = chatMessages.list("me", channelId, true, curDate);
                    JsonObject jsonObject = (JsonObject) result.getResult();
                    JsonArray jsonArray = jsonObject.getAsJsonArray("messages");
                    for (int j=0; j<jsonArray.size(); j++) {
                        jsonObject = jsonArray.get(j).getAsJsonObject();
                        if (jsonObject.get("message").getAsString().contains(condition)) {
                            String messageContent = jsonObject.get("message").getAsString();
                            String sender = jsonObject.get("sender").getAsString();
                            String dateTime = jsonObject.get("date_time").getAsString();
                            String messageId = jsonObject.get("id").getAsString();
                            Message newMessage = new Message(channelId, true, messageContent);
                            newMessage.setSender(sender);
                            newMessage.setDateTime(dateTime);
                            newMessage.setMessageId(messageId);
                            messageList.add(newMessage);
                        }
                    }
                }
                result.setResult(messageList);
            }
        } catch (ParseException e) {
            result.setErrorMessage(Result.PARSE_ERROR, "Error parsing the from date or the to date.");
            return result;
        }
        return result;
    };

    private String getChannelId(String channelName) {
        Result listResult = chatChannels.list();
        JsonObject jsonObject = (JsonObject) listResult.getResult();
        JsonArray jsonArray = jsonObject.getAsJsonArray("channels");

        String channelId = null;
        for (int i=0; i<jsonArray.size(); i++) {
            JsonElement jsonElement = jsonArray.get(i);
            if (jsonElement.getAsJsonObject().get("name").getAsString().equals(channelName)) {
                channelId = jsonElement.getAsJsonObject().get("id").getAsString();
                break;
            }
        }
        return channelId;
    }

    public Result sendMessage(String channelName, String message) {
        // Send a chat message to a given channel
        Result result = new Result();
        if (channelName == null || message == null) {
            result.setErrorMessage(
                    Result.PARAMETER_ERROR,
                    "Not all required parameters of Chat.sendMessage have been set."
            );
            return result;
        }
        String channelId = getChannelId(channelName);
        if (channelId == null) {
            result.setErrorMessage(Result.CHANNEL_ERROR, channelName);
            return result;
        }
        else {
            result = chatMessages.post(channelId, true, message);
            Message messageObj = new Message(channelId, true, message);
            messageObj.setReceiverName(channelName);
            result.setResult(messageObj);
        }
        return result;
    }

    public Result history(String channelName, String fromDate, String toDate) {
        // Retrieve the chat history of a given channel in a given time
        // range no more than 5 days
        Result result = new Result();
        List<Message> messageList = new ArrayList<>();
        if (channelName == null || fromDate == null || toDate == null) {
            result.setErrorMessage(
                    Result.PARAMETER_ERROR,
                    "Not all required parameters of Chat.sendMessage have been set."
            );
            return result;
        }
        String channelId = getChannelId(channelName);
        if (channelId == null) {
            result.setErrorMessage(Result.CHANNEL_ERROR, channelName);
            return result;
        }
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date firstDate = sdf.parse(fromDate);
                Date secondDate = sdf.parse(toDate);
                long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                if (diff > 5) {
                    result.setErrorMessage(
                            Result.TIME_RANGE_ERROR,
                            "No more than 5 days between fromDate and toDate."
                    );
                    return result;
                }
                else {
                    for (int i=0; i<=diff; i++) {
                        Calendar c = Calendar.getInstance();
                        c.setTime(firstDate);
                        c.add(Calendar.DAY_OF_MONTH, i);
                        String curDate = sdf.format(c.getTime());
                        Result tempResult = chatMessages.list("me", channelId, true, curDate);
                        JsonObject jsonObject = (JsonObject) tempResult.getResult();
                        JsonArray jsonArray = jsonObject.getAsJsonArray("messages");
                        for (int j=0; j<jsonArray.size(); j++) {
                            JsonObject tempObj = jsonArray.get(j).getAsJsonObject();
                            String messageContent = tempObj.get("message").getAsString();
                            //String sender = tempObj.get("sender").getAsString();
                            String dateTime = tempObj.get("date_time").getAsString();
                            String messageId = tempObj.get("id").getAsString();
                            Message newMessage = new Message(channelId, true, messageContent);
                            //newMessage.setSender(sender);
                            newMessage.setDateTime(dateTime);
                            newMessage.setMessageId(messageId);
                            newMessage.setReceiverName(channelName);
                            messageList.add(newMessage);
                        }
                    }
                    result.setResult(messageList);
                }
            } catch (ParseException e) {
                result.setErrorMessage(Result.PARSE_ERROR, "Error parsing the from date or the to date.");
                return result;
            }
        }
        return result;
    }

    public Result search(String channelName, String fromDate, String toDate, String condition, ISearchHandler searchHandler) {
        // Search for specific events related to chat on a given channel
        // in a given time range no more than 5 days
        Result result = new Result();
        List<Message> messageList = new ArrayList<>();
        if (channelName == null || fromDate == null || toDate == null || condition == null) {
            result.setErrorMessage(
                    Result.PARAMETER_ERROR,
                    "Not all required parameters of Chat.sendMessage have been set."
            );
            return result;
        }
        String channelId = getChannelId(channelName);
        if (channelId == null) {
            result.setErrorMessage(Result.CHANNEL_ERROR, channelName);
            return result;
        }
        else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date firstDate = sdf.parse(fromDate);
                Date secondDate = sdf.parse(toDate);
                long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                if (diff > 5) {
                    result.setErrorMessage(
                            Result.TIME_RANGE_ERROR,
                            "No more than 5 days between fromDate and toDate."
                    );
                    return result;
                }
                else {
                    result = searchHandler.search(chatMessages, channelId, fromDate, toDate, condition);
                }
            } catch (ParseException e) {
                result.setErrorMessage(Result.PARSE_ERROR, "Error parsing the from date or the to date.");
                return result;
            }
        }
        return result;
    }
}
