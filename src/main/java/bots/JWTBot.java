package bots;

import org.ini4j.Ini;
import zoomapi.JWTClient;

import java.io.FileReader;
import java.io.IOException;

public class JWTBot {
    private static String clientId;
    private static String clientSecret;

    public static void main(String[] args) {
        Ini ini;
        try (FileReader fileReader = new FileReader("src/main/java/bots/bot.ini")) {
            ini = new Ini(fileReader);
            clientId = ini.get("OAuth").fetch("client_id");
            clientSecret = ini.get("OAuth").fetch("client_secret");
            System.out.println("id: " + clientId);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JWTClient client = new JWTClient(clientId, clientSecret, null, 15);

        // test for Meeting.list
        client.getMeeting().list("me");
    }
}
