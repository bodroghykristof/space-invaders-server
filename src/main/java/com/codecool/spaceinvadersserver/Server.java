package com.codecool.spaceinvadersserver;

import com.codecool.spaceinvadersserver.room.RoomModel;
import com.codecool.spaceinvadersserver.request.RequestModel;
import com.codecool.spaceinvadersserver.room.Room;
import com.codecool.spaceinvadersserver.room.RoomHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Server {

    private RoomHandler roomHandler = new RoomHandler();
    private Gson gson = new Gson();

    public void listenForClients() throws IOException {

        ServerSocket ss = new ServerSocket(8080);

        Socket s = null;
        DataInputStream dis = null;
        DataOutputStream dos = null;

        while (true) {

            try {

                s = ss.accept();
                System.out.println("A new client is connected : " + s);
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());

                String messageFromClient = dis.readUTF();

                RequestModel requestModel = gson.fromJson(messageFromClient, RequestModel.class);

                switch (requestModel.getRequestType()) {

                    case CREATE:
                        RoomModel roomModel = requestModel.getRoomModel();
                        createNewRoom(s, dis, dos, roomModel);
                        break;

                    case LIST:
                        sendRoomData(dos);
                        break;

                    case JOIN:
                        joinRoom(s, dis, dos, requestModel);
                        break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void createNewRoom(Socket s, DataInputStream dis, DataOutputStream dos, RoomModel roomModel) {
        ClientHandler clientHandler = new ClientHandler(s, dis, dos);
        Room room = roomHandler.createNewRoom(clientHandler, roomModel);
        System.out.println("Room created");
        Thread roomThread = new Thread(room);
        roomThread.start();
    }

    private void sendRoomData(DataOutputStream dos) throws IOException {
        List<RoomModel> roomModels = roomHandler.getAll().stream().map(Room::getModel).collect(Collectors.toList());
        Type type = new TypeToken<List<RoomModel>>() {}.getType();
        dos.writeUTF(gson.toJson(roomModels, type));
    }

    private void joinRoom(Socket s, DataInputStream dis, DataOutputStream dos, RequestModel requestModel) throws Exception {
        Optional<Room> roomToJoin = roomHandler.findById(requestModel.getRoomId());
        if (roomToJoin.isPresent()) {
            roomToJoin.get().joinRoom(new ClientHandler(s, dis, dos));
        } else {
            throw new Exception("Room does not exist.");
        }
    }

}
