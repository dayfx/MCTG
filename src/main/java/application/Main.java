package application;

import server.server.Server;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("Program Started");

        //LoginService loginService = new LoginService();
        //User user = loginService.register();

        Server server = new Server(10001);
        server.start();

        //user.buyCards();
    }
}
