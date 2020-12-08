package com.sysbot32.netptalk.server;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Objects;

public class Server {
    private ServerSocketChannel serverSocket;

    public Server(int port) {
        this(new InetSocketAddress(port));
    }

    public Server(SocketAddress local) {
        try {
            serverSocket = ServerSocketChannel.open();
            serverSocket.bind(local);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection accept() {
        if (Objects.isNull(serverSocket)) {
            return null;
        }

        try {
            SocketChannel socket = serverSocket.accept();
            return new Connection(socket);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
