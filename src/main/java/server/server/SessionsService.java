package server.server;

import application.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import server.http.ContentType;
import server.http.HttpStatus;
import server.repositories.SessionsRepository;

public class SessionsService implements Service{

    SessionsRepository sessionsRepository = new SessionsRepository();

    @Override
    public Response handleRequest(Request request) {
        String method = String.valueOf(request.getMethod());

        if(method.equals("POST")) {
            try {

                ObjectMapper objectMapper = new ObjectMapper();
                User user = objectMapper.readValue(request.getBody(), User.class);

                if (sessionsRepository.loginUser(user)) {
                    Response response = new Response(HttpStatus.OK, ContentType.JSON, request.getBody());
                    return response;
                } else {
                    Response response = new Response(HttpStatus.CONFLICT, ContentType.JSON, "Login failed");
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
    }
}
