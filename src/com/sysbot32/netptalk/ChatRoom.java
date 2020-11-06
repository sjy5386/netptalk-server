package com.sysbot32.netptalk;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom {
    private String title;
    private List<User> users;
    private List<ChatMessage> chatMessages;

    public ChatRoom(String title) {
        this.title = title;
        users = new ArrayList<>();
        chatMessages = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle() {
        this.title = title;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public String toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", title);

        JSONArray jsonArrayUsers = new JSONArray();
        for (User user : users) {
            jsonArrayUsers.put(user.getName());
        }
        jsonObject.put("users", jsonArrayUsers);

        JSONArray jsonArrayChatMessages = new JSONArray();
        for (ChatMessage chatMessage : chatMessages) {
            jsonArrayChatMessages.put(chatMessage.toJSON());
        }
        jsonObject.put("chatMessages", jsonArrayChatMessages);

        return jsonObject.toString();
    }
}
