package com.sysbot32.netptalk;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private ServerSocket serverSocket;
    private List<Connection> connections;
    private ExecutorService executorService;
    private List<ChatRoom> chatRooms;

    public Server(int port) {
        System.out.println("서버를 생성합니다.");
        try {
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        connections = Collections.synchronizedList(new ArrayList<>());
        executorService = Executors.newSingleThreadExecutor();
        chatRooms = Collections.synchronizedList(new ArrayList<>());
    }

    public void start() {
        System.out.println("서버를 시작합니다.");
        if (Objects.isNull(serverSocket)) {
            System.out.println("서버를 시작할 수 없습니다.");
            return;
        }
        executorService.submit(this::accepting);
    }

    private void accepting() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket, this);
                connection.start();
                connections.add(connection);
                System.out.println(socket.getRemoteSocketAddress() + " 이(가) 연결되었습니다.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String str) {
        for (Connection connection : connections) {
            connection.write(str);
        }
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public List<ChatRoom> getChatRooms() {
        return chatRooms;
    }
}
