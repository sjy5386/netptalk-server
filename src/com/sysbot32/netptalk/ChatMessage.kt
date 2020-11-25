package com.sysbot32.netptalk

import org.json.JSONObject

data class ChatMessage(var chatType: String, var username: String, var content: String, var chatRoom: String) {
    private val type: String = "type"

    constructor(jsonObject: JSONObject) : this(
            jsonObject.getString("chatType"),
            jsonObject.getString("username"),
            jsonObject.getString("content"),
            jsonObject.getString("chatRoom"))

    fun toJSONObject(): JSONObject {
        return JSONObject()
                .put("type", type)
                .put("chatType", chatType)
                .put("username", username)
                .put("content", content)
                .put("chatRoom", chatRoom)
    }

    override fun toString(): String {
        return "${username}: $content"
    }
}
