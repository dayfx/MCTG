package server.server;

import application.Card;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import server.http.ContentType;
import server.http.HttpStatus;
import server.repositories.TransactionsRepository;

import java.util.List;
import java.util.Map;

public class TransactionsService implements Service {

    private final TransactionsRepository transactionsRepository;

    public TransactionsService() {
        this.transactionsRepository = new TransactionsRepository();
    }

    @Override
    public Response handleRequest(Request request) {

        String method = String.valueOf(request.getMethod());
        String pathParts = String.valueOf(request.getPathParts());

        if(method.equals("POST")) {
            try {

                // split up token for name

                System.out.println(request.getToken());
                String token = request.getToken();
                String tokenPart = token.substring("Authorization: Bearer ".length());
                String[] parts = tokenPart.split("-");
                String purchaser = parts[0];

                System.out.println("Purchaser: " + purchaser); // check who wants to purchase a package

                int randomPackageID = transactionsRepository.getRandomPackageID(); // get a random package ID (only useful now to check if there's even any left)

                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Integer> requestBody = objectMapper.readValue(request.getBody(), new TypeReference<Map<String, Integer>>() {}); //get package id from json body
                int packageID = requestBody.get("packageID"); // get packageID

                if(randomPackageID > 0) { // if there are packages left to buy
                    if(transactionsRepository.checkCoins(purchaser)){ // if the purchaser has more or equal to 5 coins to buy a package
                        transactionsRepository.purchaseCards(packageID, purchaser);  // assign the purchaser to the owner column of the cards and reduce their coins by 5
                    } else {
                        Response response = new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Not enough money");
                        return response;
                    }
                } else {
                    Response response = new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "No Packages available");
                    return response;
                }

                Response response = new Response(HttpStatus.OK, ContentType.JSON, "Package bought!");
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
