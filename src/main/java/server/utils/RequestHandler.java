package server.utils;

import server.http.ContentType;
import server.http.HttpStatus;
import server.server.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestHandler implements Runnable {
    private Socket clientSocket;
    private Router router;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;

    public RequestHandler(Socket clientSocket, Router router) throws IOException {
        this.clientSocket = clientSocket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        this.printWriter = new PrintWriter(this.clientSocket.getOutputStream(), true);
        this.router = router;
    }

    @Override
    public void run() {
        try {
            Response response;
            Request request = new RequestBuilder().buildRequest(this.bufferedReader);

            if (request.getPathname() == null) {
                response = new Response(
                    HttpStatus.BAD_REQUEST,
                    ContentType.JSON,
                    "[]"
                );
                printWriter.write(response.get());
            } else {
                String pathParts = String.valueOf(request.getPathParts());

                /*if(pathParts.indexOf('/') != -1){

                } else {

                }*/
                if (pathParts.equals("[users]")){
                    UserService userService = new UserService();
                    this.router.addService("/users", userService);

                    response = this.router.resolve(request.getServiceRoute()).handleRequest(request);
                    printWriter.write(response.get());
                } else if (pathParts.equals("[sessions]")){
                    SessionsService sessionsService = new SessionsService();
                    this.router.addService("/sessions", sessionsService);

                    response = this.router.resolve(request.getServiceRoute()).handleRequest(request);
                    printWriter.write(response.get());
                } else if (pathParts.equals("[packages]")){
                    PackagesService packagesService = new PackagesService();
                    this.router.addService("/packages", packagesService);

                    response = this.router.resolve(request.getServiceRoute()).handleRequest(request);
                    printWriter.write(response.get());
                }
            }

        } catch (IOException e) {
            System.err.println(Thread.currentThread().getName() + " Error: " + e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (printWriter != null) {
                    printWriter.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
