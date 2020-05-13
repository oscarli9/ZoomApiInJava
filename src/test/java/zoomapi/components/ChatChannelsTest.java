package zoomapi.components;

import org.ini4j.Ini;
import org.junit.Before;
import org.junit.Test;
import zoomapi.OAuthClient;
import zoomapi.Result;

import java.io.FileReader;
import java.io.IOException;

public class ChatChannelsTest {
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
        oAuthClient.getChatChannels().list();
    }

    @Test
    public void createTest() {
        String name = "Test Channel 2";
        String[] members = new String[]{"oscarli97+zoombot@outlook.com", "guowel2@uci.edu"};
        oAuthClient.getChatChannels().create(name, "1", members);
    }

    @Test
    public void getTest() {
        oAuthClient.getChatChannels().get("7f7aeeed526f4c05b49ba4a84d41b954");
    }

    @Test
    public void updateTest() {
        oAuthClient.getChatChannels().update("7f7aeeed526f4c05b49ba4a84d41b954", "Happy 4ever");
    }

    @Test
    public void deleteTest() {
        oAuthClient.getChatChannels().delete("e33afc80cbf94257bc35b3559c9a5c66");
    }

    @Test
    public void listChannelMembers() {
        Result result = oAuthClient.getChatChannels().listChannelMembers("7f7aeeed526f4c05b49ba4a84d41b954");
        System.out.print(result.getResult());
    }

    @Test
    public void inviteChannelMembers() {
        String[] members = new String[]{"guowel2@uci.edu"};
        oAuthClient.getChatChannels().inviteChannelMembers("2bbcba3d-f91e-4e7e-85f1-c280d7952930", members);
    }

    @Test
    public void joinTest() {
        oAuthClient.getChatChannels().join("775bee87e22e40e39542cc3055085b83");
    }

    @Test
    public void leaveTest() {
        oAuthClient.getChatChannels().leave("97269e47-3f5e-4f32-8675-8c2dfdd5f532");
    }

    @Test
    public void removeMemberTest() {
        oAuthClient.getChatChannels().removeMember("2bbcba3d-f91e-4e7e-85f1-c280d7952930", "spjbupfxqh-pakxg1s1q7q");
    }
}
