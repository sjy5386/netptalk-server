package com.sysbot32.netptalk.server

import com.sysbot32.netptalk.ChatRoom
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ChatServer(private val server: Server) {
    val chatConnections: MutableList<ChatConnection> = mutableListOf()
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    val chatRooms: MutableList<ChatRoom> = mutableListOf()

    init {
        println("서버를 생성합니다.")
    }

    fun start() {
        println("서버를 시작합니다.")
        executorService.submit(this::accepting)
    }

    private fun accepting() {
        while (true) {
            val connection: Connection? = server.accept()
            if (connection != null) {
                val chatConnection: ChatConnection = ChatConnection(connection, this)
                chatConnection.start()
                chatConnections.add(chatConnection)
                println("${connection.socketAddress}이(가) 연결되었습니다.")
            }
        }
    }

    fun write(str: String) {
        chatConnections.forEach {
            write(it, str)
        }
    }

    fun write(chatConnection: ChatConnection, str: String) {
        chatConnection.write(str)
    }

    fun findChatConnectionByUsername(username: String): ChatConnection? {
        chatConnections.forEach {
            if (it.username == username) {
                return it
            }
        }
        return null
    }

    fun findChatRoomByTitle(title: String): ChatRoom? {
        chatRooms.forEach {
            if (it.title == title) {
                return it
            }
        }
        return null
    }
}
