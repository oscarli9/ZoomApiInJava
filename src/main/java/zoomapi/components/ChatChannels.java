package zoomapi.components;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import zoomapi.Result;
import zoomapi.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatChannels extends BaseComponent {
    /**
     * Setup a new API Client
     *
     * @param baseUrl : The base URI to the API
     * @param timeout : The timeout to use for requests
     * @param config  : The config details
     */
    public ChatChannels(String baseUrl, int timeout, HashMap<String, String> config) {
        super(baseUrl, timeout, config);
    }

    public Result list() {
        Result result = new Result();
        String jsonString = getRequest("/chat/users/me/channels");
        result.setResult(new JsonParser().parse(jsonString).getAsJsonObject());
        return result;
    }

    public Result create(String name, String type, String[] members) {
        Result result = new Result();
        if (name == null || type == null || members == null) {
            result.setErrorMessage(
                    Result.PARAMETER_ERROR,
                    "Not all required parameters of ChatChannels.create have been set."
            );
            return result;
        }
        HashMap<String, Object> contentMap = new HashMap<>();
        contentMap.put("name", name);
        contentMap.put("type", type);
        List<Util.Email> memberEmails = new ArrayList<>();
        for (String memberEmail : members) {
            memberEmails.add(new Util.Email(memberEmail));
        }
        contentMap.put("members", memberEmails);
        Gson gson = new Gson();
        String content = gson.toJson(contentMap);
        String jsonString = postRequest("/chat/users/me/channels", content);
        result.setResult(new JsonParser().parse(jsonString).getAsJsonObject());
        return result;
    }

    public Result get(String channelId) {
        Result result = new Result();
        if (channelId == null) {
            result.setErrorMessage(
                    Result.PARAMETER_ERROR,
                    "The channel ID has not been set."
            );
            return result;
        }
        String jsonString = getRequest("/chat/channels/" + channelId);
        result.setResult(new JsonParser().parse(jsonString).getAsJsonObject());
        return result;
    }

    public Result update(String channelId, String name) {
        Result result = new Result();
        if (channelId == null || name == null) {
            result.setErrorMessage(
                    Result.PARAMETER_ERROR,
                    "Not all required parameters of ChatChannels.update have been set."
            );
            return result;
        }
        HashMap<String, String> contentMap = new HashMap<>();
        contentMap.put("name", name);
        Gson gson = new Gson();
        String content = gson.toJson(contentMap);
        patchRequest("/chat/channels/" + channelId, content);
        result.setResult(new Object());
        return result;
    }

    public Result delete(String channelId) {
        Result result = new Result();
        if (channelId == null) {
            result.setErrorMessage(
                    Result.PARAMETER_ERROR,
                    "The channel ID has not been set."
            );
            return result;
        }
        deleteRequest("/chat/channels/" + channelId);
        result.setResult(new Object());
        return result;
    }

    public Result listChannelMembers(String channelId) {
        Result result = new Result();
        if (channelId == null) {
            result.setErrorMessage(
                    Result.PARAMETER_ERROR,
                    "The channel ID has not been set."
            );
            return result;
        }
        String jsonString = getRequest("/chat/channels/" + channelId + "/members");
        result.setResult(new JsonParser().parse(jsonString).getAsJsonObject());
        return result;
    }

    public Result inviteChannelMembers(String channelId, String[] members) {
        Result result = new Result();
        if (channelId == null || members == null) {
            result.setErrorMessage(
                    Result.PARAMETER_ERROR,
                    "Not all required parameters of ChatChannels.inviteChannelMembers have been set."
            );
            return result;
        }
        HashMap<String, Object> contentMap = new HashMap<>();
        List<Util.Email> memberEmails = new ArrayList<>();
        for (String memberEmail : members) {
            memberEmails.add(new Util.Email(memberEmail));
        }
        contentMap.put("members", memberEmails);
        Gson gson = new Gson();
        String content = gson.toJson(contentMap);
        String jsonString = postRequest("/chat/channels/" + channelId + "/members", content);
        result.setResult(new JsonParser().parse(jsonString).getAsJsonObject());
        return result;
    }

    public Result join(String channelId) {
        Result result = new Result();
        if (channelId == null) {
            result.setErrorMessage(
                    Result.PARAMETER_ERROR,
                    "The channel ID has not been set."
            );
            return result;
        }
        String jsonString = postRequest("/chat/channels/" + channelId + "/members/me", null);
        result.setResult(new JsonParser().parse(jsonString).getAsJsonObject());
        return result;
    }

    public Result leave(String channelId) {
        Result result = new Result();
        if (channelId == null) {
            result.setErrorMessage(
                    Result.PARAMETER_ERROR,
                    "The channel ID has not been set."
            );
            return result;
        }
        deleteRequest("/chat/channels/" + channelId + "/members/me");
        result.setResult(new Object());
        return result;
    }

    public Result removeMember(String channelId, String memberId) {
        Result result = new Result();
        if (channelId == null || memberId == null) {
            result.setErrorMessage(
                    Result.PARAMETER_ERROR,
                    "Not all required parameters of ChatChannels.removeMember have been set."
            );
            return result;
        }
        deleteRequest("/chat/channels/" + channelId + "/members/" + memberId);
        result.setResult(new Object());
        return result;
    }
}
