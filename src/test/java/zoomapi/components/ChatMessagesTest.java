package zoomapi.components;

import org.ini4j.Ini;
import org.junit.Before;
import org.junit.Test;
import zoomapi.OAuthClient;
import zoomapi.Result;

import java.io.FileReader;
import java.io.IOException;

public class ChatMessagesTest {
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
    public void listTest() {
        Result result = oAuthClient.getChatMessages().list("me", "eaf23e6cc79f42278d41948417427263", true, "2020-04-27");
        System.out.println(result.getResult());
    }

    @Test
    public void postTest() {
        oAuthClient.getChatMessages().post("7f7aeeed526f4c05b49ba4a84d41b954", true, "Yayyy!");
    }

    @Test
    public void updateTest() {
       oAuthClient.getChatMessages().update("e71d0f22-1299-4185-8e1b-61e3387adc55", "eaf23e6cc79f42278d41948417427263", true, "Woww");
    }

    @Test
    public void deleteTest() {
        oAuthClient.getChatMessages().delete("f18bee32-cbd2-454b-a10f-97d4ebf2071e", "7f7aeeed526f4c05b49ba4a84d41b954", true);
    }
}
