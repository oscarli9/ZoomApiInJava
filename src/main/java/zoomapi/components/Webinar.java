package zoomapi.components;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;

public class Webinar extends BaseComponent {
    /**
     * Setup a new API Client
     * @param baseUrl : The base URI to the API
     * @param timeout : The timeout to use for requests
     * @param config  : The config details
     */
    public Webinar(String baseUrl, int timeout, HashMap<String, String> config) {
        super(baseUrl, timeout, config);
    }

    public JsonObject list(String userId, boolean print) {
        String jsonString = getRequest("/users/" + userId + "/webinars");
        if (print) System.out.println(jsonString);
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }

    public JsonObject create(String userId, HashMap<String, String> webinarConfig, boolean print) {
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("{\"topic\":\"");
        contentBuilder.append(webinarConfig.get("topic"));
        contentBuilder.append("\",\"type\":");
        contentBuilder.append(webinarConfig.get("type"));
        contentBuilder.append(",\"start_time\":\"");
        contentBuilder.append(webinarConfig.get("start_time"));
        contentBuilder.append("\",\"duration\":");
        contentBuilder.append(webinarConfig.get("duration"));
        contentBuilder.append(",\"timezone\":\"");
        contentBuilder.append(webinarConfig.get("timezone"));
        contentBuilder.append("\",\"password\":\"");
        contentBuilder.append(webinarConfig.get("password"));
        contentBuilder.append("\",\"agenda\":\"");
        contentBuilder.append("\"}");
        String content = contentBuilder.toString();
        String jsonString = postRequest("/users/" + userId + "/webinars", content);
        if (print) System.out.println(jsonString);
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }

    public JsonObject update(String webinarId, HashMap<String, String> webinarConfig, boolean print) {
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("{\"topic\":\"");
        contentBuilder.append(webinarConfig.get("topic"));
        contentBuilder.append("\",\"type\":");
        contentBuilder.append(webinarConfig.get("type"));
        contentBuilder.append(",\"start_time\":\"");
        contentBuilder.append(webinarConfig.get("start_time"));
        contentBuilder.append("\",\"duration\":");
        contentBuilder.append(webinarConfig.get("duration"));
        contentBuilder.append(",\"timezone\":\"");
        contentBuilder.append(webinarConfig.get("timezone"));
        contentBuilder.append("\",\"password\":\"");
        contentBuilder.append(webinarConfig.get("password"));
        contentBuilder.append("\",\"agenda\":\"");
        contentBuilder.append("\"}");
        String content = contentBuilder.toString();
        String jsonString = postRequest("/webinars/" + webinarId, content);
        if (print) System.out.println(jsonString);
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }

    public JsonObject delete(String webinarId, boolean print) {
        String jsonString = deleteRequest("/webinars/" + webinarId);
        if (print) System.out.println(jsonString);
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }

    public JsonObject end(String webinarId, boolean print) {
        String jsonString = putRequest("/webinars/" + webinarId + "/status", "{\"action\":\"end\"}");
        if (print) System.out.println(jsonString);
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }

    public JsonObject get(String webinarId, boolean print) {
        String jsonString = getRequest("/webinars/" + webinarId);
        if (print) System.out.println(jsonString);
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }

    public JsonObject register(String webinarId, HashMap<String, String> userInfo, boolean print) {
        String content = "{\"email\":\"" +
                userInfo.get("email") +
                "\",\"first_name\":\"" +
                userInfo.get("first_name") +
                "\",\"last_name\":\"" +
                userInfo.get("last_name") +
                "\"}";
        String jsonString = postRequest("/webinars/" + webinarId + "/registrants", content);
        if (print) System.out.println(jsonString);
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }
}
