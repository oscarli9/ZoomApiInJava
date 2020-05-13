package zoomapi;

import zoomapi.components.*;

/**
 * Zoom.us REST API Java Client
 */
public class Client {
    private static final String apiBaseUrl = "https://api.zoom.us/v2";

    public static abstract class ZoomClient extends Util.ApiClient {
        private ChatChannels chatChannels;
        private ChatMessages chatMessages;
        private Chat chat;
        private Meeting meeting;
        private Recording recording;
        private Report report;
        private User user;
        private Webinar webinar;

        /**
         * Setup a new API Client
         * @param apiKey : The Zooom.us API key
         * @param apiSecret: The Zoom.us API secret
         * @param dataType: The expected return data type
         * @param timeout : The timeout to use for API requests
         */
        public ZoomClient(String apiKey, String apiSecret, String dataType, int timeout) {
            super(apiBaseUrl, timeout, null);

            // Setup the config details
            config.put("api_key", apiKey);
            config.put("api_secret", apiSecret);
            if (dataType == null || dataType.equals("")) config.put("data_type", "application/json");
            else config.put("data_type", dataType);
        }

        public abstract void refreshToken();

        public String getApiKey() {
            return config.get("api_key");
        }

        public void setApiKey(String apiKey) {
            config.put("api_key", apiKey);
            refreshToken();
        }

        public String getApiSecret() {
            return config.get("api_secret");
        }

        public void setApiSecret(String apiSecret) {
            config.put("api_secret", apiSecret);
            refreshToken();
        }

        public ChatChannels getChatChannels() {
            if (chatChannels == null) return new ChatChannels(apiBaseUrl, 15, config);
            return chatChannels;
        }

        public ChatMessages getChatMessages() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (chatMessages == null) return new ChatMessages(apiBaseUrl, 15, config);
            return chatMessages;
        }

        public Chat getChat() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (chat == null) return new Chat(apiBaseUrl, 15, config);
            return chat;
        }

        public Meeting getMeeting() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (meeting == null) return new Meeting(apiBaseUrl, 15, config);
            return meeting;
        }

        public Recording getRecording() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (recording == null) return new Recording(apiBaseUrl, 15, config);
            return recording;
        }

        public Report getReport() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (report == null) return new Report(apiBaseUrl, 15, config);
            return report;
        }

        public User getUser() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (user == null) return new User(apiBaseUrl, 15, config);
            return user;
        }

        public Webinar getWebinar() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (webinar == null) return new Webinar(apiBaseUrl, 15, config);
            return webinar;
        }
    }
}
