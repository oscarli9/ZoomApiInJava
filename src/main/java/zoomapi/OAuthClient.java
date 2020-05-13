package zoomapi;

import zoomapi.handlers.INewMemberHandler;
import zoomapi.handlers.INewMessageHandler;
import zoomapi.handlers.IUpdateMessageHandler;

/**
 * Zoom.us REST API Java Client
 */
public class OAuthClient extends Client.ZoomClient {
    private Downloader downloader;

    /**
     * Create a new Zoom client
     * @param clientId: The Zooom.us client id for this OAuth bot
     * @param clientSecret: The Zoom.us client secret for this OAuth bot
     * @param dataType: The expected return data type
     * @param timeout: The time out to use for API requests
     */
    public OAuthClient(String clientId, String clientSecret, String port, String dataType, int timeout) {
        super(clientId, clientSecret, dataType, timeout);

        // Add the specific config details
        config.put("client_id", clientId);
        config.put("client_secret", clientSecret);
        config.put("port", port);
        config.put("token", Util.getOauthToken(clientId, clientSecret, port));
    }

    @Override
    public void refreshToken() {
        config.put("token", Util.getOauthToken(
                config.get("client_id"),
                config.get("client_secret"),
                config.get("port")
                ));
    }

    public void newMessageEvent(String channelName, String eventType, INewMessageHandler h) {
        if (downloader == null) downloader = new Downloader(this);
        downloader.addEvent(eventType, channelName, h);
    }

    public void updatedMessageEvent(String channelName, String eventType, IUpdateMessageHandler h) {
        if (downloader == null) downloader = new Downloader(this);
        downloader.addEvent(eventType, channelName, h);
    }

    public void newMemberEvent(String eventType, INewMemberHandler h) {
        if (downloader == null) downloader = new Downloader(this);
        downloader.addEvent(eventType, null, h);
    }

    public void runDownloader() {
        if (downloader == null) {
            System.err.println("No event registered yet!");
        } else downloader.start();
    }
}
