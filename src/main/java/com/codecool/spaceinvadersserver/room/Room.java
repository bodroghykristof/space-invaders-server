package com.codecool.spaceinvadersserver.room;

import com.codecool.spaceinvadersserver.ClientHandler;
import com.codecool.spaceinvadersserver.request.RequestModel;
import com.codecool.spaceinvadersserver.request.RequestType;

import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Room implements Runnable {

    private final ClientHandler owner;
    private final List<ClientHandler> spectators;
    private final RoomModel roomModel;

    public Room(ClientHandler clientHandler, RoomModel roomModel) {
        this.owner = clientHandler;
        this.spectators = new ArrayList<>();
        this.roomModel = roomModel;
    }

    public int getId() {
        return roomModel.getId();
    }

    @Override
    public void run() {
        listenToOwnerMessages();
    }

    private void listenToOwnerMessages() {

        while (!Thread.currentThread().isInterrupted()) {

            try {
                processOwnerMessage();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void processOwnerMessage() throws IOException {
        RequestModel messageFromOwner = owner.readMessage();
        if (messageFromOwner.getRequestType() == RequestType.EXIT) {
            destroyRoom();
        } else {
            deliverOwnerMessage(messageFromOwner);
        }
    }

    private void destroyRoom() throws IOException {
        for (ClientHandler spectator : spectators) {
            if (spectator.isSocketAlive()) {
                spectator.sendMessage("Owner has exit the room");
            }
        }
        Thread.currentThread().interrupt();
    }

    private void deliverOwnerMessage(RequestModel messageFromOwner) throws IOException {
        Set<ClientHandler> spectatorsToRemove = new HashSet<>();
        for (ClientHandler spectator: spectators) {
            try {
                spectator.sendMessage(messageFromOwner.getMessage());
            } catch (SocketException e) {
                spectatorsToRemove.add(spectator);
                System.out.println("spectator removed");
            }
        }
        spectatorsToRemove.forEach(spectators::remove);
    }

    public void joinRoom(ClientHandler spectator) {
        spectators.add(spectator);
    }

    public RoomModel getModel() {
        return roomModel;
    }
}
