package com.sysbot32.netptalk.server

import com.sysbot32.netptalk.ChatMessage
import com.sysbot32.netptalk.ChatRoom
import org.json.JSONObject
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ChatConnection(val connection: Connection, private val chatServer: ChatServer) {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    lateinit var username: String

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
                    val title: String = jsonObject.getString("title")
                    when (jsonObject.getString("action")) {
                        "add" -> {
                            val chatRoom = ChatRoom(title)
                            chatServer.chatRooms.add(chatRoom)
                            chatRoom.users.add(username)
                            chatServer.write(this, received)
                        }
                        "invite" -> {
                            val chatRoom = chatServer.findChatRoomByTitle(title)
                            if (chatRoom != null) {
                                val invitee: String = jsonObject.getString("invitee")
                                chatRoom.users.add(invitee)
                                chatServer.findChatConnectionByUsername(invitee)?.let {
                                    chatServer.write(it, received)
                                }
                            }
                        }
                        "leave" -> {
                            val chatRoom = chatServer.findChatRoomByTitle(title)
                            chatRoom?.users?.remove(jsonObject.getString("username"))
                        }
                    }
                }
                "login" -> {
                    username = jsonObject.getString("username")
                    println("${username}님이 로그인했습니다.")
                    chatServer.chatRooms.forEach {
                        if (it.users.contains(username)) {
                            connection.write(it.toJSONObject().put("action", "add").toString())
                        }
                    }
                }
            }
        }
        println("${connection.socketAddress}에서 연결을 해제합니다.")
        connection.disconnect()
        chatServer.chatConnections.remove(this)
    }
}
