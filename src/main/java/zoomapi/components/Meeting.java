package zoomapi.components;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;

public class Meeting extends BaseComponent {
    /**
     * Setup a new API Client
     * @param baseUrl : The base URI to the API
     * @param timeout : The timeout to use for requests
     * @param config  : The config details
     */
    public Meeting(String baseUrl, int timeout, HashMap<String, String> config) {
        super(baseUrl, timeout, config);
    }

    public JsonObject list(String userId) {
        return list(userId, true);
    }

    public JsonObject list(String userId, boolean print) {
        String jsonString = getRequest("/users/" + userId + "/meetings");
        if (print) System.out.println(jsonString);
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }

    public JsonObject create(String userId, HashMap<String, String> meetingConfig) {
        return create(userId, meetingConfig, true);
    }

    public JsonObject create(String userId, HashMap<String, String> meetingConfig, boolean print) {
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("{\"topic\":\"");
        contentBuilder.append(meetingConfig.get("topic"));
        contentBuilder.append("\",\"type\":");
        contentBuilder.append(meetingConfig.get("type"));
        contentBuilder.append(",\"start_time\":\"");
        contentBuilder.append(meetingConfig.get("start_time"));
        contentBuilder.append("\",\"duration\":");
        contentBuilder.append(meetingConfig.get("duration"));
        contentBuilder.append(",\"timezone\":\"");
        contentBuilder.append(meetingConfig.get("timezone"));
        contentBuilder.append("\",\"password\":\"");
        contentBuilder.append(meetingConfig.get("password"));
        contentBuilder.append("\",\"agenda\":\"");
        contentBuilder.append("\"}");
        String content = contentBuilder.toString();
        String jsonString = postRequest("/users/" + userId + "/meetings", content);
        if (print) System.out.println(jsonString);
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }

    public JsonObject get(String meetingId) {
        return get(meetingId, true);
    }

    public JsonObject get(String meetingId, boolean print) {
        String jsonString = getRequest("/meetings/" + meetingId);
        if (print) System.out.println(jsonString);
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }

    public JsonObject update(String meetingId, HashMap<String, String> meetingConfig) {
        return update(meetingId, meetingConfig, true);
    }

    public JsonObject update(String meetingId, HashMap<String, String> meetingConfig, boolean print) {
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("{\"topic\":\"");
        contentBuilder.append(meetingConfig.get("topic"));
        contentBuilder.append("\",\"type\":");
        contentBuilder.append(meetingConfig.get("type"));
        contentBuilder.append(",\"start_time\":\"");
        contentBuilder.append(meetingConfig.get("start_time"));
        contentBuilder.append("\",\"duration\":");
        contentBuilder.append(meetingConfig.get("duration"));
        contentBuilder.append(",\"timezone\":\"");
        contentBuilder.append(meetingConfig.get("timezone"));
        contentBuilder.append("\",\"password\":\"");
        contentBuilder.append(meetingConfig.get("password"));
        contentBuilder.append("\",\"agenda\":\"");
        contentBuilder.append("\"}");
        String content = contentBuilder.toString();
        String jsonString = patchRequest("/meetings/" + meetingId, content);
        if (print) System.out.println(jsonString);
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }

    public JsonObject delete(String meetingId) {
        return delete(meetingId, true);
    }

    public JsonObject delete(String meetingId, boolean print) {
        String jsonString = deleteRequest("/meetings/" + meetingId);
        if (print) System.out.println(jsonString);
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }
}
