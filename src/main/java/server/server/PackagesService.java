package server.server;

import application.Card;
import application.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import server.http.ContentType;
import server.http.HttpStatus;
import server.repositories.PackagesRepository;

import java.util.List;

public class PackagesService implements Service {

    private final PackagesRepository packagesRepository;

    public PackagesService() {
        this.packagesRepository = new PackagesRepository();
    }

    @Override
    public Response handleRequest(Request request) {
        String method = String.valueOf(request.getMethod());

        if(method.equals("POST")) {
            try {

                // Parse into list of Card Objects
                ObjectMapper objectMapper = new ObjectMapper();
                List<Card> cards = objectMapper.readValue(request.getBody(), new TypeReference<List<Card>>() {});

                int packageId = packagesRepository.getNextPackageId();

                for (Card card : cards) {
                    card.setPackageid(packageId);
                }

                packagesRepository.createCard(cards);

                Response response = new Response(HttpStatus.CREATED, ContentType.JSON, "Package created");
                return response;

            } catch (Exception e) {
                e.printStackTrace();
                Response response = new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Invalid JSON format");
                return response;
            }
        } else if(method.equals("GET")) {
            try {

                String token = request.getToken();
                if(token == null) {
                    Response response = new Response(HttpStatus.UNAUTHORIZED, ContentType.JSON, "Unauthorized");
                    return response;
                }

                String tokenPart = token.substring("Authorization: Bearer ".length());
                String[] parts = tokenPart.split("-");
                String user = parts[0];

                if (token != null) { // if authorization header isn't empty
                    System.out.println(token);
                    System.out.println(user);
                    String content = packagesRepository.displayCards(user); //get all the cards that belong to user

                    Response response = new Response(HttpStatus.OK, ContentType.PLAIN_TEXT, content);
                    return response;
                }

                Response response = new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Invalid JSON format");
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
