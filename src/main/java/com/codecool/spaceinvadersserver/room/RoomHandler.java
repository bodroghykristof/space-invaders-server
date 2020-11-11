package com.codecool.spaceinvadersserver.room;

import com.codecool.spaceinvadersserver.ClientHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomHandler {

    private List<Room> rooms = new ArrayList<>();


    public Optional<Room> findById(int roomId) {
        return rooms.stream().filter(room -> room.getId() == roomId).findFirst();
    }

    public Room createNewRoom(ClientHandler clientHandler, RoomModel roomModel) {
        roomModel.setId(rooms.size() + 1);
        Room room = new Room(clientHandler, roomModel);
        rooms.add(room);
        return room;
    }

    public List<Room> getAll() {
        return rooms;
    }
}
