package com.sysbot32.netptalk;

import org.json.JSONObject;

public class ChatMessage {
    private String type = "chat";
    private String chatType;
    private String username;
    private String content;
    private String chatRoom;

    public ChatMessage(String chatType, String username, String content, String chatRoom) {
        this.chatType = chatType;
        this.username = username;
        this.content = content;
        this.chatRoom = chatRoom;
    }

    public ChatMessage(JSONObject jsonObject) {
        chatType = jsonObject.getString("chatType");
        username = jsonObject.getString("username");
        content = jsonObject.getString("content");
        chatRoom = jsonObject.getString("chatRoom");
    }

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }

    public String getUsername() {
        return username;
    }

    public void setUser(String sender) {
        this.username = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent() {
        this.content = content;
    }

    public String getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(String chatRoom) {
        this.chatRoom = chatRoom;
    }

    public String toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", type);
        jsonObject.put("chatType", chatType);
        jsonObject.put("username", username);
        jsonObject.put("content", content);
        jsonObject.put("chatRoom", chatRoom);
        return jsonObject.toString();
    }

    @Override
    public String toString() {
        return username + ": " + content;
    }
}
