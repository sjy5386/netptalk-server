package com.sysbot32.netptalk

import org.json.JSONObject

data class ChatRoom(var title: String) {
    private val type: String = "chatRoom"
    val users: MutableList<String> = mutableListOf()
    val chatMessages: MutableList<ChatMessage> = mutableListOf()

    fun toJSONObject(): JSONObject = JSONObject()
            .put("type", type)
            .put("title", title)
            .put("users", users)
}
