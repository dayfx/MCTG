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
        String pathParts = String.valueOf(request.getPathParts());

        UserRepository userRepository = new UserRepository();

        System.out.println();

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

        } else if (method.equals("GET")) {
            System.out.println("we in atleast");
            System.out.println(request.getToken());
            System.out.println(pathParts);

            String[] parts = pathParts.split(", ");
            String extractedName = parts[1].replace("]", "");
            System.out.println(extractedName);

            parts = request.getToken().split("Authorization: Bearer ");

           // System.out.println(request.getToken().replace("Authorization: Bearer ", "").equals(extractedName));
           // System.out.println(parts[1]);
           // System.out.println(extractedName + "-mctgToken");

            try{
                if(parts[1].equals(extractedName + "-mtcgToken")) { // if token from header matches name in api
                    String content = userRepository.displayUserData(extractedName); // show user data //NVM I JUST CANT DO IT HAHA IDK HOW TO DO IT HELPPPPP ILL SKIP THIS

                    Response response = new Response(HttpStatus.OK, ContentType.PLAIN_TEXT, content); // need display first?
                    return response;
                }

            } catch (Exception e) {
                e.printStackTrace();
                Response response = new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Invalid JSON format");
                return response;
            }
        }

        //if no request or whatever
        else {
            Response response = new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, request.getBody());
            return response;
        }
        Response response = new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, request.getBody());
        return response;
    }
}
