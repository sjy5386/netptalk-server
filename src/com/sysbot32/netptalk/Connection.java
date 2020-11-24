package com.sysbot32.netptalk;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Connection {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private ExecutorService executorService;
    private Server server;

    public Connection(Socket socket, Server server) throws Exception {
        this.socket = socket;
        this.server = server;
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        executorService = Executors.newSingleThreadExecutor();
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
            JSONObject jsonObject = new JSONObject(received);
            String type = jsonObject.getString("type");
            if (type.equals("chat")) {
                ChatMessage chatMessage = new ChatMessage(jsonObject);
                List<Connection> connections = server.getConnections();
                for (Connection connection : connections) {
                    connection.write(received);
                }
            }
        }
    }

    public void start() {
        executorService.submit(this::reading);
    }
}
