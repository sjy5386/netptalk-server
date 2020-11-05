package com.sysbot32.netptalk;

import java.net.Socket;

public class Connection {
    private Socket socket;

    public Connection(Socket socket) {
        this.socket = socket;
    }
}
