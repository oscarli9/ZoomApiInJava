package zoomapi.components;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;

public class Recording extends BaseComponent {
    /**
     * Setup a new API Client
     * @param baseUrl : The base URI to the API
     * @param timeout : The timeout to use for requests
     * @param config  : The config details
     */
    public Recording(String baseUrl, int timeout, HashMap<String, String> config) {
        super(baseUrl, timeout, config);
    }

    public JsonObject list(String userId, HashMap<String, String> queryConfig) {
        return list(userId, queryConfig, true);
    }

    public JsonObject list(String userId, HashMap<String, String> queryConfig, boolean print) {
        StringBuilder endpointBuilder = new StringBuilder();
        endpointBuilder.append("/users/").append(userId).append("/recordings");
        if (queryConfig.containsKey("start_time") || queryConfig.containsKey("end_time")) {
            endpointBuilder.append("?");
            if (queryConfig.containsKey("start_time"))
                endpointBuilder.append("from=").append(queryConfig.get("start_time&"));
            if (queryConfig.containsKey("end_time"))
                endpointBuilder.append("to=").append(queryConfig.get("end_time&"));
            endpointBuilder.deleteCharAt(endpointBuilder.lastIndexOf("&"));
        }
        String endpoint = endpointBuilder.toString();

        String jsonString = getRequest(endpoint);
        if (print) System.out.println(jsonString);
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }

    public JsonObject get(String meetingId) {
        return get(meetingId, true);
    }

    public JsonObject get(String meetingId, boolean print) {
        String jsonString = getRequest("/meetings/" + meetingId + "/recordings");
        if (print) System.out.println(jsonString);
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }

    public JsonObject delete(String meetingId) {
        return delete(meetingId, true);
    }

    public JsonObject delete(String meetingId, boolean print) {
        String jsonString = getRequest("/meetings/" + meetingId + "/recordings");
        if (print) System.out.println(jsonString);
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }
}
