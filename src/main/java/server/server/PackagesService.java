package server.server;

import application.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import server.http.ContentType;
import server.http.HttpStatus;
import server.repositories.PackagesRepository;

public class PackagesService implements Service {

    PackagesRepository repository = new PackagesRepository();

    @Override
    public Response handleRequest(Request request) {
        String method = String.valueOf(request.getMethod());

        if(method.equals("POST")) {
            try {

            } catch (Exception e) {
                e.printStackTrace();
                Response response = new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Invalid JSON format");
                return response;
            }

        } else {
            Response response = new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, request.getBody());
            return response;
        }
        return null;
    }
}
