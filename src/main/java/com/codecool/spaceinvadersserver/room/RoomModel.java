package com.codecool.spaceinvadersserver.room;

import java.io.Serializable;
import java.util.Calendar;


public class RoomModel implements Serializable {
    private Calendar time;
    private int id;
    private String roomName;

    public RoomModel(Calendar time, String roomName) {
        this.time = time;
        this.roomName = roomName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Calendar getTime() {
        return time;
    }

    public int getId() {
        return id;
    }

    public String getRoomName() {
        return roomName;
    }
}
