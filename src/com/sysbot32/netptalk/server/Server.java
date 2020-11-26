package com.sysbot32.netptalk.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

public class Server {
    private ServerSocket serverSocket;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection accept() {
        if (Objects.isNull(serverSocket)) {
            return null;
        }

        try {
            Socket socket = serverSocket.accept();
            return new Connection(socket);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
