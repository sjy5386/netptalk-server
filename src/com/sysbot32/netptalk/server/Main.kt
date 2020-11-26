package com.sysbot32.netptalk.server

fun main(args: Array<String>) {
    ChatServer(Server(30001)).start()
}
