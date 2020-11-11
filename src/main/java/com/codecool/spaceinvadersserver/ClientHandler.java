package com.codecool.spaceinvadersserver;

import com.codecool.spaceinvadersserver.request.RequestModel;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {


    private Gson gson = new Gson();
    private final Socket socket;
    private final DataOutputStream dos;
    private final DataInputStream dis;

    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos) {
        this.socket = s;
        this.dis = dis;
        this.dos = dos;
    }

    public RequestModel readMessage() throws IOException {
        return gson.fromJson(dis.readUTF(), RequestModel.class);
    }

    public void sendMessage(String message) throws IOException {
        if (!socket.isClosed()) {
            dos.writeUTF(message);
        }
    }

    public boolean isSocketAlive() {
        return !socket.isClosed();
    }

}
