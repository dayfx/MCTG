package server.server;

import application.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import server.http.ContentType;
import server.http.HttpStatus;
import server.repositories.UserRepository;

public class UserService implements Service{
    @Override
    public Response handleRequest(Request request) {
        String method = String.valueOf(request.getMethod());

        UserRepository userRepository = new UserRepository();

        if(method.equals("POST")) {
            try {

                ObjectMapper objectMapper = new ObjectMapper();
                User user = objectMapper.readValue(request.getBody(), User.class);


                if(!userRepository.checkUser(user)) {
                    userRepository.createUser(user);
                    Response response = new Response(HttpStatus.CREATED, ContentType.JSON, request.getBody());
                    return response;
                } else {
                    Response response = new Response(HttpStatus.CONFLICT, ContentType.JSON, "User already exists");
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
