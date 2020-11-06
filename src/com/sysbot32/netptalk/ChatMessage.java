package com.sysbot32.netptalk;

import org.json.JSONObject;

public class ChatMessage {
    private String type = "chat";
    private String chatType;
    private String sender;
    private String content;

    public ChatMessage(String chatType, String sender, String content) {
        this.chatType = chatType;
        this.sender = sender;
        this.content = content;
    }

    public ChatMessage(JSONObject jsonObject) {
        chatType = jsonObject.getString("chatType");
        sender = jsonObject.getString("user");
        content = jsonObject.getString("content");
    }

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }

    public String getSender() {
        return sender;
    }

    public void setUser(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent() {
        this.content = content;
    }

    public String toJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", type);
        jsonObject.put("chatType", chatType);
        jsonObject.put("sender", sender);
        jsonObject.put("content", content);
        return jsonObject.toString();
    }

    @Override
    public String toString() {
        return sender + ": " + content;
    }
}
