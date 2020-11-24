package com.sysbot32.netptalk;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Connection {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private ExecutorService executorService;
    private SocketAddress socketAddress;
    private Server server;
    private String username;

    public Connection(Socket socket, Server server) throws Exception {
        this.socket = socket;
        this.server = server;
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        executorService = Executors.newSingleThreadExecutor();
        socketAddress = socket.getRemoteSocketAddress();
    }

    private String read() {
        if (Objects.isNull(bufferedReader)) {
            return null;
        }

        try {
            return bufferedReader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void write(String str) {
        if (Objects.isNull(bufferedWriter)) {
            return;
        }

        try {
            bufferedWriter.write(str);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reading() {
        while (true) {
            String received = read();
            if (Objects.isNull(received)) {
                break;
            }
            System.out.println(received);
            JSONObject jsonObject = new JSONObject(received);
            String type = jsonObject.getString("type");
            if (type.equals("chat")) {
                ChatMessage chatMessage = new ChatMessage(jsonObject);
                System.out.println(chatMessage);
                List<ChatRoom> chatRooms = server.getChatRooms();
                for (ChatRoom chatRoom : chatRooms) {
                    if (chatRoom.getTitle().equals(chatMessage.getChatRoom())) {
                        chatRoom.getChatMessages().add(chatMessage);
                        break;
                    }
                }
                server.send(received);
            } else if (type.equals("chatRoom")) {
                ChatRoom chatRoom = new ChatRoom(jsonObject.getString("title"));
                if (jsonObject.getString("action").equals("add")) {
                    server.getChatRooms().add(chatRoom);
                    server.send(chatRoom.toJSON());
                }
            } else if (type.equals("login")) {
                username = jsonObject.getString("username");
                System.out.println(username + "님이 로그인했습니다.");
            }
        }
        disconnect();
    }

    public void start() {
        executorService.submit(this::reading);
    }

    public void disconnect() {
        System.out.println(socketAddress + " 연결을 해제합니다.");
        try {
            bufferedReader.close();
            bufferedWriter.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        bufferedReader = null;
        bufferedWriter = null;
        socket = null;
        server.getConnections().remove(this);
    }
}
