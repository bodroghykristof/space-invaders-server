package com.codecool.spaceinvadersserver.app;

import com.codecool.spaceinvadersserver.Server;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {

        Server server = new Server();
        server.listenForClients();

    }
}


