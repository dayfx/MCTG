package server.server;

public class UserService implements Service{
    @Override
    public Response handleRequest(Request request) {
        Response response;
        if(request.getMethod().equals("POST")) {
            System.out.println(request.getBody());


        }
        return null;
    }
}
