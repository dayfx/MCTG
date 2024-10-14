package server.server;

import server.http.ContentType;
import server.http.HttpStatus;

public class UserService implements Service{
    @Override
    public Response handleRequest(Request request) {
        if(request.getMethod().equals("POST")) {
            System.out.println(request.getBody());


        }
        Response response = new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, request.getBody());
        return response;
    }
}
