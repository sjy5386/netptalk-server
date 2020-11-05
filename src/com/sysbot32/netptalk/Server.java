package com.sysbot32.netptalk;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private ServerSocket serverSocket;
    private List<Connection> connections;
    private ExecutorService executorService;

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        connections = new ArrayList<>();
        executorService = Executors.newSingleThreadExecutor();
        System.out.println("서버를 생성했습니다.");
    }

    public void start() {
        executorService.submit(this::accepting);
        System.out.println("서버를 시작했습니다.");
    }

    private void accepting() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket);
                connection.start();
                connections.add(connection);
                System.out.println(socket.getRemoteSocketAddress() + " 이(가) 연결되었습니다.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
