package com.sysbot32.netptalk;

import java.net.ServerSocket;
import java.net.Socket;

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
        try {
            Socket socket = serverSocket.accept();
            return new Connection(socket);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
