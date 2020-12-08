package com.sysbot32.netptalk.server;

import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Objects;

public class Connection {
    private SocketChannel socket;
    private SocketAddress socketAddress;

    public Connection(SocketChannel socket) throws Exception {
        this.socket = socket;
        socketAddress = socket.socket().getRemoteSocketAddress();
    }

    public ByteBuffer read() {
        if (Objects.isNull(socket) || !socket.isConnected()) {
            return null;
        }

        ByteBuffer buf = ByteBuffer.allocate(Integer.BYTES);
        ByteBuffer data;
        try {
            if (socket.read(buf) < 0) {
                return null;
            }
            buf.flip();
            int size = buf.getInt();
            data = ByteBuffer.allocate(size);
            while (data.hasRemaining()) {
                socket.read(data);
            }
            data.flip();
        } catch (Exception e) {
            return null;
        }
        return data;
    }

    public synchronized void write(ByteBuffer data) {
        if (Objects.isNull(socket) || !socket.isConnected()) {
            return;
        }

        int size = data.capacity();
        ByteBuffer buf = ByteBuffer.allocate(Integer.BYTES + size);
        buf.putInt(size);
        buf.put(data);
        buf.flip();
        try {
            socket.write(buf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        socket = null;
    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }
}
