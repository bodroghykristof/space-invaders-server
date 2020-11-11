package com.codecool.spaceinvadersserver.request;

import com.codecool.spaceinvadersserver.room.RoomModel;

public class RequestModel {
    private RequestType requestType;
    private int roomId;
    private String message;
    private RoomModel roomModel;

    public RequestModel(RequestType requestType) {
        this.requestType = requestType;
    }

    public RequestModel(RequestType requestType, int roomId) {
        this.requestType = requestType;
        this.roomId = roomId;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RoomModel getRoomModel() {
        return roomModel;
    }

    public void setRoomModel(RoomModel roomModel) {
        this.roomModel = roomModel;
    }
}
