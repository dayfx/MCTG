package server.server;

import application.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import server.http.ContentType;
import server.http.HttpStatus;

public class UserService implements Service{
    @Override
    public Response handleRequest(Request request) {
        String method = String.valueOf(request.getMethod());
        if(method.equals("POST")) {
            try {

                ObjectMapper objectMapper = new ObjectMapper();

                User user = objectMapper.readValue(request.getBody(), User.class);

                String username = user.getUsername();
                String password = user.getPassword();

                System.out.println("into post it goes");
                System.out.println("This Username: " + username);
                System.out.println("This Password: " + password);

                Response response = new Response(HttpStatus.CREATED, ContentType.JSON, request.getBody());
                return response;

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
