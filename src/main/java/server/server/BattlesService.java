package server.server;

import application.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import server.http.ContentType;
import server.http.HttpStatus;
import server.repositories.BattlesRepository;

import java.util.ArrayList;
import java.util.List;

public class BattlesService implements Service {

    private final BattlesRepository battlesRepository;
    private final List<String> waitingUsers = new ArrayList<>();

    public BattlesService() {
        this.battlesRepository = new BattlesRepository();
    }

    @Override
    public Response handleRequest(Request request) {
        String method = String.valueOf(request.getMethod());

        if(method.equals("POST")) {
            try {

                System.out.println(request.getToken());
                String token = request.getToken();
                String tokenPart = token.substring("Authorization: Bearer ".length());
                String[] parts = tokenPart.split("-");
                String user = parts[0];

                System.out.println("User wants to battle: " + user); // check who wants to battle

                waitingUsers.add(user);
                if (waitingUsers.size() == 2) {
                    battlesRepository.executeBattle(waitingUsers.get(0), waitingUsers.get(1));

                    String content = battlesRepository.executeBattle(waitingUsers.get(0), waitingUsers.get(1));

                    Response response = new Response(HttpStatus.OK, ContentType.PLAIN_TEXT, content);
                    return response;
                }

            } catch (Exception e) {
                e.printStackTrace();
                Response response = new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Invalid JSON format");
                return response;
            }

        } else {
            Response response = new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, request.getBody());
            return response;
        }
        Response response = new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, request.getBody());
        return response; //not sure why response here but otherwise error
    }
}
