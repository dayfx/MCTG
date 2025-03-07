package server.server;

import application.Card;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import server.http.ContentType;
import server.http.HttpStatus;
import server.repositories.DeckRepository;

import java.util.List;

public class DeckService implements Service {

    private final DeckRepository deckRepository;

    public DeckService() {
        this.deckRepository = new DeckRepository();
    }

    @Override
    public Response handleRequest(Request request) {

        String method = String.valueOf(request.getMethod());
        String pathParts = String.valueOf(request.getPathParts());

        if(method.equals("PUT")) {
            try {

                // split up token for name

                System.out.println(request.getToken());
                String token = request.getToken();
                String tokenPart = token.substring("Authorization: Bearer ".length());
                String[] parts = tokenPart.split("-");
                String user = parts[0];

                System.out.println("User's deck to be configured: " + user); // check who wants to purchase a package

                ObjectMapper objectMapper = new ObjectMapper();
                if(request.getBody() != null) {
                    List<String> cardIDs = objectMapper.readValue(request.getBody(), new TypeReference<List<String>>() {});

                    if (cardIDs.size() != 4){
                        Response response = new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Bad Request - needs to be 4 cards");
                        return response;
                    }

                    if(deckRepository.checkConfiguration(user)){ //if cards of the one's to be used are already ready. send 4xx response and configured deck
                        String content = deckRepository.displayDeck(user);

                        Response response = new Response(HttpStatus.CONFLICT, ContentType.PLAIN_TEXT, content);
                        return response;
                    }

                    for (String cardID : cardIDs) {
                        deckRepository.setCardReady(cardID);
                    }

                    Response response = new Response(HttpStatus.OK, ContentType.JSON, "OK");
                    return response;
                }

                Response response = new Response(HttpStatus.OK, ContentType.JSON, "stuff happened!");
                return response;

            } catch (Exception e) {
                e.printStackTrace();
                Response response = new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Invalid JSON format");
                return response;
            }

        } else if(method.equals("GET")) {
            try {

                // split up token for name

                System.out.println(request.getToken());
                String token = request.getToken();
                String tokenPart = token.substring("Authorization: Bearer ".length());
                String[] parts = tokenPart.split("-");
                String user = parts[0];

                System.out.println(pathParts);
                if("format=plain".equals(request.getParams())){
                    String content = deckRepository.displayDeckPlain(user);

                    Response response = new Response(HttpStatus.OK, ContentType.PLAIN_TEXT, content);
                    return response;
                }

                System.out.println("User's deck to be displayed: " + user); // check who wants to display a package

                String content = deckRepository.displayDeck(user);

                Response response = new Response(HttpStatus.OK, ContentType.PLAIN_TEXT, content);
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
