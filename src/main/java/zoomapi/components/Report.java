package zoomapi.components;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;

public class Report extends BaseComponent {
    /**
     * Setup a new API Client
     * @param baseUrl : The base URI to the API
     * @param timeout : The timeout to use for requests
     * @param config  : The config details
     */
    public Report(String baseUrl, int timeout, HashMap<String, String> config) {
        super(baseUrl, timeout, config);
    }

    public JsonObject getUserReport(String userId, HashMap<String, String> queryConfig) {
        return getUserReport(userId, queryConfig, true);
    }

    public JsonObject getUserReport(String userId, HashMap<String, String> queryConfig, boolean print) {
        StringBuilder endpointBuilder = new StringBuilder();
        endpointBuilder.append("/report/users/").append(userId).append("/meetings");
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

    public JsonObject getAccountReport(HashMap<String, String> queryConfig) {
        return getAccountReport(queryConfig, true);
    }

    public JsonObject getAccountReport(HashMap<String, String> queryConfig, boolean print) {
        StringBuilder endpointBuilder = new StringBuilder();
        endpointBuilder.append("/report/users");
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
}
