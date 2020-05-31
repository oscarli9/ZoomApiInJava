package zoomapi;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import zoomapi.components.Chat;
import zoomapi.components.ChatChannels;
import zoomapi.handlers.IEventHandler;
import zoomapi.handlers.INewMemberHandler;
import zoomapi.handlers.INewMessageHandler;
import zoomapi.handlers.IUpdateMessageHandler;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static zoomapi.Downloader.ApiEvent.*;

@SuppressWarnings("unchecked")
public class Downloader extends Thread {
    public enum ApiEvent {
        NEW_MESSAGE_EVENT,
        UPDATED_MESSAGE_EVENT,
        NEW_MEMBER_EVENT
    }
    public static ArrayList<ApiEvent> eventDic = new ArrayList<>(
            Arrays.asList(NEW_MESSAGE_EVENT, UPDATED_MESSAGE_EVENT, NEW_MEMBER_EVENT)
    );

    private BlockingQueue<TaskObject> queue;
    private Chat chat;
    private ChatChannels chatChannels;

    public Downloader(OAuthClient oAuthClient) {
        queue = new LinkedBlockingQueue<>();
        chatChannels = oAuthClient.getChatChannels();
        chat = oAuthClient.getChat();
    }

    public boolean addEvent(ApiEvent eventType, String identity, IEventHandler h) {
        if (eventType == null || h == null) {
            System.err.println("Not all required parameters have been set.");
            return false;
        } else if (identity == null && (eventType.equals(NEW_MESSAGE_EVENT) || eventType.equals(UPDATED_MESSAGE_EVENT))) {
            System.err.println("Not all required parameters have been set.");
            return false;
        } else if (!eventDic.contains(eventType)) {
            System.err.println("Invalid event type.");
            return false;
        }
        if (eventDic.contains(eventType)) {
            try {
                TaskObject taskObject = new TaskObject(eventType, identity, h);
                queue.put(taskObject);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    public boolean removeEvent(ApiEvent eventType, String identity) {
        if (eventType == null) {
            System.err.println("Not all required parameters have been set.");
            return false;
        } else if (!eventDic.contains(eventType)) {
            System.err.println("Invalid event type.");
            return false;
        }
        queue.removeIf(obj -> obj.getEventType().equals(eventType) && obj.getIdentity().equals(identity));
        return true;
    }

    private String getChannelId(String channelName) {
        Result listResult = chatChannels.list();
        JsonObject jsonObject = (JsonObject) listResult.getResult();
        JsonArray jsonArray = jsonObject.getAsJsonArray("channels");

        String channelId = null;
        for (int i=0; i<jsonArray.size(); i++) {
            JsonElement jsonElement = jsonArray.get(i);
            if (jsonElement.getAsJsonObject().get("name").getAsString().equals(channelName)) {
                channelId = jsonElement.getAsJsonObject().get("id").getAsString();
                break;
            }
        }
        return channelId;
    }

    @Override
    public void run() {
        HashMap<String, List<Message>> channelMessages = new HashMap<>();
        HashMap<String, List<Member>> channelMembers = new HashMap<>();

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(date);

        for (TaskObject taskObject : queue) {
            ApiEvent eventType = taskObject.getEventType();
            String identity = taskObject.getIdentity();
            String channelId = getChannelId(identity);
            if (eventType.equals(NEW_MESSAGE_EVENT) || eventType.equals(UPDATED_MESSAGE_EVENT)) {
                if (!channelMessages.containsKey(channelId)) {
                    Result result = chat.history(identity, dateString, dateString);
                    if (result.isSuccessful()) {
                        List<Message> messages = (List<Message>) result.getResult();
                        channelMessages.put(channelId, messages);
                    } else System.err.println(result.getErrorMessage());
                }
            } else {
                Result result = chatChannels.list();
                JsonObject jsonObject = (JsonObject) result.getResult();
                JsonArray jsonArray = jsonObject.getAsJsonArray("channels");

                for (int i=0; i<jsonArray.size(); i++) {
                    JsonElement jsonElement = jsonArray.get(i);
                    channelId = jsonElement.getAsJsonObject().get("id").getAsString();
                    String channelName = jsonElement.getAsJsonObject().get("name").getAsString();
                    Result memberResult = chatChannels.listChannelMembers(channelId);

                    if (result.isSuccessful()) {
                        JsonObject memberJsonObject = (JsonObject) memberResult.getResult();
                        JsonArray memberJsonArray = memberJsonObject.getAsJsonArray("members");
                        List<Member> members = new ArrayList<>();
                        for (int j=0; j<memberJsonArray.size(); j++) {
                            JsonObject tempObj = memberJsonArray.get(j).getAsJsonObject();
                            String id = tempObj.get("id").getAsString();
                            String email = tempObj.get("email").getAsString();
                            String name = tempObj.get("name").getAsString();
                            String role = tempObj.get("role").getAsString();
                            Member member = new Member(email)
                                    .setChannelId(channelId)
                                    .setChannelName(channelName)
                                    .setId(id)
                                    .setName(name)
                                    .setRole(role);
                            members.add(member);
                        }
                        channelMembers.put(channelId, members);
                    } else System.err.println(result.getErrorMessage());
                }
            }
        }

        while (true) {
            if (!queue.isEmpty()) {
                date = new Date();
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                dateString = sdf.format(date);
                TaskObject curObj = queue.poll();
                assert curObj != null;
                ApiEvent eventType = curObj.getEventType();
                String identity = curObj.getIdentity();
                String channelId = getChannelId(identity);
                List<Message> newMessages = new ArrayList<>();
                List<Message> updatedMessages = new ArrayList<>();
                List<Member> newMembers = new ArrayList<>();
                if (eventType.equals(NEW_MESSAGE_EVENT)) {
                    List<Message> messages = (List<Message>) chat.history(identity, dateString, dateString).getResult();
                    List<Message> oldMessages = channelMessages.get(channelId);
                    for (Message m : messages) {
                        boolean isNew = true;
                        for (Message m2 : oldMessages) {
                            if (m.getMessageId().equals(m2.getMessageId())) {
                                isNew = false;
                                break;
                            }
                        }
                        if (isNew) newMessages.add(m);
                    }
                    oldMessages.addAll(newMessages);
                    oldMessages.addAll(updatedMessages);
                    channelMessages.put(channelId, oldMessages);
                } else if (eventType.equals(UPDATED_MESSAGE_EVENT)) {
                    List<Message> messages = (List<Message>) chat.history(identity, dateString, dateString).getResult();
                    List<Message> oldMessages = channelMessages.get(channelId);
                    for (Message m : messages) {
                        for (Message m2 : oldMessages) {
                            if (m.getMessageId().equals(m2.getMessageId()) && !m.getMessage().equals(m2.getMessage())) {
                                updatedMessages.add(m);
                                oldMessages.remove(m2);
                                break;
                            }
                        }
                    }
                    oldMessages.addAll(updatedMessages);
                    channelMessages.put(channelId, oldMessages);
                } else {
                    Result result = chatChannels.list();
                    JsonObject jsonObject = (JsonObject) result.getResult();
                    JsonArray jsonArray = jsonObject.getAsJsonArray("channels");

                    for (int i=0; i<jsonArray.size(); i++) {
                        JsonElement jsonElement = jsonArray.get(i);
                        channelId = jsonElement.getAsJsonObject().get("id").getAsString();
                        String channelName = jsonElement.getAsJsonObject().get("name").getAsString();
                        Result memberResult = chatChannels.listChannelMembers(channelId);
                        if (result.isSuccessful()) {
                            JsonObject memberJsonObject = (JsonObject) memberResult.getResult();
                            JsonArray memberJsonArray = memberJsonObject.getAsJsonArray("members");
                            List<Member> oldMembers = channelMembers.get(channelId);
                            for (int j=0; j<memberJsonArray.size(); j++) {
                                boolean isNew = true;
                                JsonObject tempObj = memberJsonArray.get(j).getAsJsonObject();
                                String id = tempObj.get("id").getAsString();
                                String email = tempObj.get("email").getAsString();
                                String name = tempObj.get("name").getAsString();
                                String role = tempObj.get("role").getAsString();
                                for (Member m : oldMembers) {
                                    if (m.getEmail().equals(email)) {
                                        isNew = false;
                                        break;
                                    }
                                }
                                if (isNew) {
                                    Member member = new Member(email)
                                            .setChannelId(channelId)
                                            .setChannelName(channelName)
                                            .setId(id)
                                            .setName(name)
                                            .setRole(role);
                                    newMembers.add(member);
                                }
                            }
                            oldMembers.addAll(newMembers);
                            channelMembers.put(channelId, oldMembers);
                        } else System.err.println(result.getErrorMessage());
                    }
                }

                if (eventType.equals(NEW_MESSAGE_EVENT)) {
                    INewMessageHandler h = (INewMessageHandler) curObj.getEventHandler();
                    for (Message m : newMessages)
                        new Thread(() -> h.handle(m)).start();
                }

                if (eventType.equals(UPDATED_MESSAGE_EVENT)) {
                    IUpdateMessageHandler h = (IUpdateMessageHandler) curObj.getEventHandler();
                    for (Message m : updatedMessages)
                        new Thread(() -> h.handle(m)).start();
                }

                if (eventType.equals(NEW_MEMBER_EVENT)) {
                    INewMemberHandler h = (INewMemberHandler) curObj.getEventHandler();
                    for (Member m : newMembers)
                        new Thread(() -> h.handle(m)).start();
                }

                try {
                    queue.put(curObj);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
