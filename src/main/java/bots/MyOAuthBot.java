package bots;

import org.ini4j.Ini;
import zoomapi.*;
import zoomapi.components.Chat;
import zoomapi.components.ChatChannels;
import zoomapi.components.ChatMessages;
import zoomapi.handlers.INewMemberHandler;
import zoomapi.handlers.INewMessageHandler;
import zoomapi.handlers.IUpdateMessageHandler;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import zoomapi.Downloader.ApiEvent;

@SuppressWarnings("unchecked")
public class MyOAuthBot {
    private static String clientId;
    private static String clientSecret;
    private static String port;
    private static String browserPath;

    public static void main(String[] args) {
        Ini ini;
        try (FileReader fileReader = new FileReader("src/main/java/bots/bot.ini")) {
            ini = new Ini(fileReader);
            clientId = ini.get("OAuth").fetch("client_id");
            clientSecret = ini.get("OAuth").fetch("client_secret");
            port = ini.get("OAuth").fetch("port");
            browserPath = ini.get("OAuth").fetch("browser_path");
            System.out.println("id: " + clientId + " browser: " + browserPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        OAuthClient oAuthClient = new OAuthClient(clientId, clientSecret, port, null, 15);
        ChatChannels chatChannels = oAuthClient.getChatChannels();
        ChatMessages chatMessages = oAuthClient.getChatMessages();
        Chat chat = oAuthClient.getChat();
        Result result = null;
/*
        // test for ChatChannels.list
        result = oAuthClient.getChatChannels().list();
        if (result.isSuccessful()) System.out.println(result.getResult());
        else System.out.println(result.getErrorMessage());

        // test for ChatChannels.create
        String name = "WTF";
        String[] members = new String[]{"oscarli97+zoombot@outlook.com", "guowel2@uci.edu"};
        result = chatChannels.create(name, "1", members);
        if (result.isSuccessful()) System.out.println(result.getResult());
        else System.out.println(result.getErrorMessage());

        // test for ChatChannels.get
        result = chatChannels.get("7f7aeeed526f4c05b49ba4a84d41b954");
        if (result.isSuccessful()) System.out.println(result.getResult());
        else System.out.println(result.getErrorMessage());

        // test for ChatChannels.update
        String newName = "happy forever";
        chatChannels.update("7f7aeeed526f4c05b49ba4a84d41b954", newName);

        // test for ChatChannels.delete
        chatChannels.delete("a4431158-176e-4f03-8209-cf1b87d3f438");

        // test for ChatChannels.listChannelMembers
        result = chatChannels.listChannelMembers("7f7aeeed526f4c05b49ba4a84d41b954");
        if (result.isSuccessful()) System.out.println(result.getResult());
        else System.out.println(result.getErrorMessage());

        // test for ChatChannels.inviteChannelMembers
        String[] newMembers = new String[]{"guowel2@uci.edu"};
        result = chatChannels.inviteChannelMembers("775bee87e22e40e39542cc3055085b83", newMembers);
        if (result.isSuccessful()) System.out.println(result.getResult());
        else System.out.println(result.getErrorMessage());

        // test for ChatChannels.leave
        chatChannels.leave("775bee87e22e40e39542cc3055085b83");

        // test for ChatChannels.removeMember
        chatChannels.removeMember("775bee87e22e40e39542cc3055085b83", "spjbupfxqh-pakxg1s1q7q");

        // test for ChatMessages.list
        result = chatMessages.list("me", "eaf23e6cc79f42278d41948417427263", true, "2020-05-13");
        if (result.isSuccessful()) System.out.println(result.getResult());
        else System.out.println(result.getErrorMessage());

        // test for ChatMessages.post
        result = chatMessages.post("7f7aeeed526f4c05b49ba4a84d41b954", true, "A nice day.");
        if (result.isSuccessful()) System.out.println(result.getResult());
        else System.out.println(result.getErrorMessage());

        // test for ChatMessages.update
        chatMessages.update("2d794d5d-f82c-43ee-8bbd-21afde2eb569", "7f7aeeed526f4c05b49ba4a84d41b954", true, "Yo");

        // test for ChatMessages.delete
        chatMessages.delete("99D5BB81-8F89-45CC-A6C9-ED24F16D99FC","7f7aeeed526f4c05b49ba4a84d41b954", true);
*/
        // test for ChatChannels.join
        //chatChannels.join("7f7aeeed526f4c05b49ba4a84d41b954");
        //if (result.isSuccessful()) System.out.println(result.getResult());
        //else System.out.println(result.getErrorMessage());

        // test for Chat.sendMessage
        result = chat.sendMessage("hahaha", "I'm doing good.");
        if (result.isSuccessful()) {
            Message message = (Message) result.getResult();
            System.out.println("******************************");
            System.out.println("Message sent successfully!");
            System.out.println("Channel Id: " + message.getReceiver());
            System.out.println("Channel Name: " + message.getReceiverName());
            System.out.println("Message: " + message.getMessage());
            System.out.println("******************************");
        } else System.out.println(result.getErrorMessage());

        // test for Chat.history
        String fromDate = "2020-02-29";
        String toDate = "2020-03-22";
        String channelName = "hahaha";
        result = chat.history(channelName, fromDate, toDate);
        if (result.isSuccessful()) {
            List<Message> messageList = (List<Message>) result.getResult();
            System.out.println("******************************");
            System.out.println("Chat history of " + channelName + " from " + fromDate + " to " + toDate + ":");
            for (Message message : messageList) {
                System.out.print("Message ID: " + message.getMessageId() + " ");
                System.out.print("Time: " + message.getDateTime() + " ");
                System.out.print("Sender: " + message.getSender() + " ");
                System.out.println("Message: " + message.getMessage());
            }
            System.out.println("******************************");
        } else System.out.println(result.getErrorMessage());

        fromDate = "2020-04-25";
        toDate = "2020-04-28";
        result = chat.history(channelName, fromDate, toDate);
        if (result.isSuccessful()) {
            List<Message> messageList = (List<Message>) result.getResult();
            System.out.println("******************************");
            System.out.println("Chat history of " + channelName + " from " + fromDate + " to " + toDate + ":");
            for (Message message : messageList) {
                System.out.print("Message ID: " + message.getMessageId() + " ");
                System.out.print("Time: " + message.getDateTime() + " ");
                System.out.print("Sender: " + message.getSender() + " ");
                System.out.println("Message: " + message.getMessage());
            }
            System.out.println("******************************");
        } else System.out.println(result.getErrorMessage());

        //  test for Chat.search
        fromDate = "2020-04-21";
        toDate = "2020-04-27";
        String condition = "guo";
        result = chat.search(channelName, fromDate, toDate, condition, Chat.SEARCH_BY_SENDER);
        if (result.isSuccessful()) {
            List<Message> messageList = (List<Message>) result.getResult();
            System.out.println("******************************");
            System.out.println("Channel Name: " + channelName);
            System.out.println("Events with sender \"" + condition + "\" from " + fromDate + " to " + toDate + ": ");
            for (Message message : messageList) {
                System.out.print("Message ID: " + message.getMessageId() + " ");
                System.out.print("Time: " + message.getDateTime() + " ");
                System.out.print("Sender: " + message.getSender() + " ");
                System.out.println("Message: " + message.getMessage());
            }
            System.out.println("******************************");
        } else System.out.println(result.getErrorMessage());

        fromDate = "2020-04-26";
        toDate = "2020-04-28";
        result = chat.search(channelName, fromDate, toDate, condition, Chat.SEARCH_BY_SENDER);
        if (result.isSuccessful()) {
            List<Message> messageList = (List<Message>) result.getResult();
            System.out.println("******************************");
            System.out.println("Channel Name: " + channelName);
            System.out.println("Events with sender \"" + condition + "\" from " + fromDate + " to " + toDate + ": ");
            for (Message message : messageList) {
                System.out.print("Message ID: " + message.getMessageId() + " ");
                System.out.print("Time: " + message.getDateTime() + " ");
                System.out.print("Sender: " + message.getSender() + " ");
                System.out.println("Message: " + message.getMessage());
            }
            System.out.println("******************************");
        } else System.out.println(result.getErrorMessage());

        condition = "me";
        result = chat.search(channelName, fromDate, toDate, condition, Chat.SEARCH_BY_MESSAGE);
        if (result.isSuccessful()) {
            List<Message> messageList = (List<Message>) result.getResult();
            System.out.println("******************************");
            System.out.println("Channel Name: " + channelName);
            System.out.println("Events with message \"" + condition + "\" from " + fromDate + " to " + toDate + ": ");
            for (Message message : messageList) {
                System.out.print("Message ID: " + message.getMessageId() + " ");
                System.out.print("Time: " + message.getDateTime() + " ");
                System.out.print("Sender: " + message.getSender() + " ");
                System.out.println("Message: " + message.getMessage());
            }
            System.out.println("******************************");
        } else System.out.println(result.getErrorMessage());

        // test for Milestone 4 - newMessagesEvent/updatedMessagesEvent/newMemberEvent
        // might take a long time to get responses
        INewMessageHandler newMessageHandler = (Message message) -> {
            System.out.println("******************************");
            System.out.println("New message: ");
            System.out.print("Message ID: " + message.getMessageId() + " ");
            System.out.print("Time: " + message.getDateTime() + " ");
            System.out.println("Message: " + message.getMessage());
            System.out.println("******************************");
            return null;
        };
        IUpdateMessageHandler updateMessageHandler = (Message message) -> {
            System.out.println("******************************");
            System.out.println("Updated message: ");
            System.out.print("Message ID: " + message.getMessageId() + " ");
            System.out.print("Time: " + message.getDateTime() + " ");
            System.out.println("Message: " + message.getMessage());
            System.out.println("******************************");
            return null;
        };
        INewMemberHandler newMemberHandler = (Member member) -> {
            System.out.println("******************************");
            System.out.println("New member: ");
            System.out.print("Channel ID: " + member.getChannelId() + " ");
            System.out.print("Channel Name: " + member.getChannelName() + " ");
            System.out.print("ID: " + member.getId() + " ");
            System.out.print("Email: " + member.getEmail() + " ");
            System.out.print("Name: " + member.getName() + " ");
            System.out.println("Role: " + member.getRole());
            System.out.println("******************************");
            return null;
        };
        oAuthClient.newMessageEvent("hahaha", ApiEvent.NEW_MESSAGE_EVENT, newMessageHandler);
        oAuthClient.updatedMessageEvent("hahaha", ApiEvent.UPDATED_MESSAGE_EVENT, updateMessageHandler);
        oAuthClient.newMemberEvent(ApiEvent.NEW_MEMBER_EVENT, newMemberHandler);
        oAuthClient.runDownloader();
    }
}
