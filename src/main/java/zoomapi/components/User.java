package zoomapi.components;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;

public class User extends BaseComponent {
    /**
     * Setup a new API Client
     * @param baseUrl : The base URI to the API
     * @param timeout : The timeout to use for requests
     * @param config  : The config details
     */
    public User(String baseUrl, int timeout, HashMap<String, String> config) {
        super(baseUrl, timeout, config);
    }

    public JsonObject list() {
        return list(true);
    }

    public JsonObject list(boolean print) {
        String jsonString = getRequest("/users");
        if (print) System.out.println(jsonString);
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }

    public JsonObject create(HashMap<String, String> userInfo) {
        return create(userInfo, true);
    }

    public JsonObject create(HashMap<String, String> userInfo, boolean print) {
        String content = "{\"action\":\"create\",\"user_info\":{\"email\":\"" +
                userInfo.get("email") +
                "\",\"type\":" +
                userInfo.get("type") +
                ",\"first_name\":\"" +
                userInfo.get("first_name") +
                "\",\"last_name\":\"" +
                "\"}}";
        String jsonString = postRequest("/users", content);
        if (print) System.out.println(jsonString);
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }

    public JsonObject get(String userId) {
        return get(userId, true);
    }

    public JsonObject get(String userId, boolean print) {
        String jsonString = getRequest("/users/" + userId);
        if (print) System.out.println(jsonString);
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }

    public JsonObject update(String userId, HashMap<String, String> userInfo) {
        return update(userId, userInfo, true);
    }

    public JsonObject update(String userId, HashMap<String, String> userInfo, boolean print) {
        String content = "{\"first_name\":\"" +
                userInfo.get("first_name") +
                "\",\"last_name\":\"" +
                userInfo.get("last_name") +
                "\",\"type\":" +
                userInfo.get("type") +
                ",\"pmi\":" +
                userInfo.get("pmi") +
                ",\"timezone\":\"" +
                userInfo.get("timezone") +
                "\",\"dept\":\"" +
                userInfo.get("dept") +
                "\",\"vanity_name\":\"" +
                userInfo.get("vanity_name") +
                "\",\"host_key\":\"" +
                userInfo.get("host_key") +
                "\",\"cms_user_id\":\"" +
                userInfo.get("cms_user_id") +
                "\",\"job_title\":\"" +
                userInfo.get("job_title") +
                "\",\"company\":\"" +
                userInfo.get("company") +
                "\",\"location\":\"" +
                userInfo.get("location") +
                "\",\"phone_number\":\"" +
                userInfo.get("phone_number") +
                "\",\"phone_country\":\"" +
                userInfo.get("phone_country") +
                "\"}";
        String jsonString = patchRequest("/users/" + userId, content);
        if (print) System.out.println(jsonString);
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }

    public JsonObject delete(String userId) {
        return delete(userId, true);
    }

    public JsonObject delete(String userId, boolean print) {
        String jsonString = deleteRequest("/users/" + userId);
        if (print) System.out.println(jsonString);
        return new JsonParser().parse(jsonString).getAsJsonObject();
    }
}
