package com.sysbot32.netptalk

import org.json.JSONObject

data class ChatMessage(
        val chatType: String,
        val username: String,
        val content: String,
        val chatRoom: String,
        val timestamp: Long
) {
    private val type: String = "chat"

    constructor(jsonObject: JSONObject) : this(
            jsonObject.getString("chatType"),
            jsonObject.getString("username"),
            jsonObject.getString("content"),
            jsonObject.getString("chatRoom"),
            jsonObject.getLong("timestamp")
    )

    fun toJSONObject(): JSONObject {
        return JSONObject()
                .put("type", type)
                .put("chatType", chatType)
                .put("username", username)
                .put("content", content)
                .put("chatRoom", chatRoom)
                .put("timestamp", timestamp)
    }

    override fun toString(): String {
        return "${username}: $content"
    }
}
