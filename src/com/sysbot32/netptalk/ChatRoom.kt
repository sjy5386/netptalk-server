package com.sysbot32.netptalk

import org.json.JSONArray
import org.json.JSONObject

data class ChatRoom(var title: String) {
    private val type: String = "chatRoom"
    private val users: MutableList<String> = mutableListOf()
    val chatMessages: MutableList<ChatMessage> = mutableListOf()

    fun toJSONObject(): JSONObject {
        val jsonObject: JSONObject = JSONObject()
                .put("type", type)
                .put("title", title)
                .put("users", users)
        val jsonArrayChatMessages: JSONArray = JSONArray()
        chatMessages.forEach {
            jsonArrayChatMessages.put(it)
        }
        return jsonObject.put("chatMessages", jsonArrayChatMessages)
    }
}
