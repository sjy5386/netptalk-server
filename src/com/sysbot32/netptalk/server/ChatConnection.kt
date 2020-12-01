package com.sysbot32.netptalk.server

import com.sysbot32.netptalk.ChatMessage
import com.sysbot32.netptalk.ChatRoom
import org.json.JSONObject
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ChatConnection(val connection: Connection, private val chatServer: ChatServer) {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private lateinit var username: String

    fun start() {
        executorService.submit(this::reading)
    }

    private fun reading() {
        while (true) {
            val received: String = connection.read() ?: break
            println(received)
            val jsonObject = JSONObject(received)
            when (jsonObject.getString("type")) {
                "chat" -> {
                    val chatMessage = ChatMessage(jsonObject)
                    run {
                        chatServer.chatRooms.forEach {
                            if (it.title == chatMessage.chatRoom) {
                                it.chatMessages.add(chatMessage)
                                return@run
                            }
                        }
                    }
                    chatServer.write(received)
                }
                "chatRoom" -> {
                    val chatRoom = ChatRoom(jsonObject.getString("title"))
                    if (jsonObject.getString("action") == "add") {
                        chatServer.chatRooms.add(chatRoom)
                        chatServer.write(received)
                    }
                }
                "login" -> {
                    username = jsonObject.getString("username")
                    println("${username}님이 로그인했습니다.")
                    chatServer.chatRooms.forEach {
                        connection.write(it.toJSONObject().put("action", "add").toString())
                    }
                }
            }
        }
        println("${connection.socketAddress}에서 연결을 해제합니다.")
        connection.disconnect()
        chatServer.chatConnections.remove(this)
    }
}
