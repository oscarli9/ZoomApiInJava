package zoomapi.components;

import org.ini4j.Ini;
import org.junit.Before;
import org.junit.Test;
import zoomapi.models.Message;
import zoomapi.OAuthClient;
import zoomapi.models.Result;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("unchecked")
public class ChatTest {
    Ini ini;
    OAuthClient oAuthClient;

    @Before
    public void setup() {
        try (FileReader fileReader = new FileReader("/Users/oscarli/IdeaProjects/Spring2020/262P_ZoomApiInJava/src/main/java/bots/bot.ini")) {
            ini = new Ini(fileReader);
            String cid = ini.get("OAuth").fetch("client_id");
            String cSecret = ini.get("OAuth").fetch("client_secret");
            String port = ini.get("OAuth").fetch("port");
            String browserPath = ini.get("OAuth").fetch("browser_path");
            System.out.println(cid + "\n" + cSecret + "\n" + port + "\n" + browserPath);

            oAuthClient = new OAuthClient(cid, cSecret, port, null, 15);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sendMessageTest() {
        Chat chat = oAuthClient.getChat();
        chat.sendMessage("hahaha", "I love u");
        chat.sendMessage("hahaha", "U love me");
    }

    @Test
    public void historyTest() {
        Chat chat = oAuthClient.getChat();
        chat.history("hahaha", "2020-04-21", "2020-04-27");
        List<Message> messageList = (List<Message>) chat.history("hahaha", "2020-05-28", "2020-05-31").getResult();
        for (Message message : messageList) {
            System.out.println(message.getMessageId());
            System.out.println(message.getDateTime());
            System.out.println(message.getSender());
            System.out.println(message.getMessage());
        }
    }

    @Test
    public void searchTest() {
        Chat chat = oAuthClient.getChat();
        //chat.search("hahaha", "2020-04-21", "2020-04-27", "guo", Chat.SEARCH_BY_SENDER);
        Result result = chat.search("hahaha", "2020-04-27", "2020-04-28", "guo", Chat.SEARCH_BY_SENDER);
        List<Message> messageList = (List<Message>) result.getResult();
        for (Message m : messageList) System.out.println(m.getMessageId());
        //chat.search("hahaha", "2020-04-23", "2020-04-28", "me", Chat.SEARCH_BY_MESSAGE);
        //chat.search("hahaha", "2020-05-12", "2020-05-13", "zoom", Chat.SEARCH_BY_SENDER);
    }
}
