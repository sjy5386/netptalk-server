package com.sysbot32.netptalk.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Objects;

public class Connection {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private SocketAddress socketAddress;

    public Connection(Socket socket) throws Exception {
        this.socket = socket;
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        socketAddress = socket.getRemoteSocketAddress();
    }

    public String read() {
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

        new Thread(() -> {
            try {
                bufferedWriter.write(str);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void disconnect() {
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
    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }
}
