package zoomapi;

public class JWTClient extends Client.ZoomClient {
    private static final String apiBaseUrl = "https://api.zoom.us/v2";

    /**
     * Setup a new Zoom Client
     * @param clientId: The Zooom.us API key
     * @param clientSecret: The Zoom.us API secret
     * @param dataType: The expected return data type
     * @param timeout: The timeout to use for API requests
     */
    public JWTClient(String clientId, String clientSecret, String dataType, int timeout) {
        super(clientId, clientSecret, dataType, timeout);

        // Add the specific config details
        config.put("client_id", clientId);
        config.put("client_secret", clientSecret);
        config.put("token", Util.generateJWT(clientId, clientSecret));
    }

    @Override
    public void refreshToken() {
        config.put("token", Util.generateJWT(config.get("client_id"), config.get("client_secret")));
    }
}
