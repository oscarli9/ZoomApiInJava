package zoomapi.components;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import zoomapi.Result;

import java.util.HashMap;

public class ChatMessages extends BaseComponent {
    /**
     * Setup a new API Client
     * @param baseUrl : The base URI to the API
     * @param timeout : The timeout to use for requests
     * @param config  : The config details
     */
    public ChatMessages(String baseUrl, int timeout, HashMap<String, String> config) {
        super(baseUrl, timeout, config);
    }

    public Result list(String userId, String receiver, boolean isChannel, String date) {
        Result result = new Result();
        if (userId == null || receiver == null || date == null) {
            result.setErrorMessage(
                    Result.PARAMETER_ERROR,
                    "Not all required parameters of ChatMessages.list have been set."
            );
            return result;
        }
        StringBuilder endpointBuilder = new StringBuilder();
        endpointBuilder.append("/chat/users/").append(userId).append("/messages?");
        endpointBuilder.append("date=").append(date).append("&");
        if (isChannel) endpointBuilder.append("to_channel");
        else endpointBuilder.append("to_contact");
        endpointBuilder.append("=").append(receiver);
        String endpoint = endpointBuilder.toString();
        String jsonString = getRequest(endpoint);
        result.setResult(new JsonParser().parse(jsonString).getAsJsonObject());
        return result;
    }

    public Result post(String receiver, boolean isChannel, String message) {
        Result result = new Result();
        if (receiver == null || message == null) {
            result.setErrorMessage(
                    Result.PARAMETER_ERROR,
                    "Not all required parameters of ChatMessages.post have been set."
            );
            return result;
        }
        HashMap<String, String> contentMap = new HashMap<>();
        contentMap.put("message", message);
        if (isChannel) contentMap.put("to_channel", receiver);
        else contentMap.put("to_contact", receiver);
        Gson gson = new Gson();
        String content = gson.toJson(contentMap);
        String jsonString = postRequest("/chat/users/me/messages", content);
        result.setResult(new JsonParser().parse(jsonString).getAsJsonObject());
        return result;
    }

    public Result update(String messageId, String receiver, boolean isChannel, String message) {
        Result result = new Result();
        if (messageId == null || receiver == null || message == null) {
            result.setErrorMessage(
                    Result.PARAMETER_ERROR,
                    "Not all required parameters of ChatMessages.update have been set."
            );
            return result;
        }
        HashMap<String, String> contentMap = new HashMap<>();
        contentMap.put("message", message);
        if (isChannel) contentMap.put("to_channel", receiver);
        else contentMap.put("to_contact", receiver);
        Gson gson = new Gson();
        String content = gson.toJson(contentMap);
        putRequest("/chat/users/me/messages/" + messageId, content);
        result.setResult(new Object());
        return result;
    }

    public Result delete(String messageId, String receiver, boolean isChannel) {
        Result result = new Result();
        if (messageId == null || receiver == null) {
            result.setErrorMessage(
                    Result.PARAMETER_ERROR,
                    "Not all required parameters of ChatMessages.delete have been set."
            );
            return result;
        }
        StringBuilder endpointBuilder = new StringBuilder();
        endpointBuilder.append("/chat/users/me/messages/").append(messageId).append("?");
        if (isChannel) endpointBuilder.append("to_channel");
        else endpointBuilder.append("to_contact");
        endpointBuilder.append("=").append(receiver);
        String endpoint = endpointBuilder.toString();
        deleteRequest(endpoint);
        result.setResult(new Object());
        return result;
    }
}
