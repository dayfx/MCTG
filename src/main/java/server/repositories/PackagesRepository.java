package server.repositories;

import application.Card;
import server.server.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PackagesRepository {

    DatabaseConnection databaseConnection = new DatabaseConnection();

    public void createCard(List<Card> cards) {
        try {
            Connection connection = databaseConnection.connect();

            String query = "INSERT INTO cards (id, name, damage, packageid) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for (Card card : cards) {
                preparedStatement.setString(1, card.getId());
                preparedStatement.setString(2, card.getCardName());
                preparedStatement.setDouble(3, card.getDamage());
                preparedStatement.setInt(4, card.getPackageid());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
            connection.close();
            System.out.println("Cards created successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getNextPackageId() {
        int nextId = 1; // Default first package ID

        try {
            Connection connection = databaseConnection.connect();
            String query = "SELECT MAX(packageid) FROM cards";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                nextId = resultSet.getInt(1) + 1;
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nextId;
    }

    public String displayCards(String user){

        StringBuilder cardList = new StringBuilder(); // to build one singular string

        try {
            Connection connection = databaseConnection.connect();

            String query = "SELECT * FROM cards WHERE owner = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, user);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) { // check if the user even has cards
                cardList.append("No Cards Found");
                return cardList.toString();
            }

            while (resultSet.next()){ //turning everything into one string
                cardList.append("xx-----------CARD----------xx\n");
                cardList.append("PackageID: ").append(resultSet.getInt("packageid")).append("\n");
                cardList.append("ID: ").append(resultSet.getString("id")).append("\n");
                cardList.append("Name: ").append(resultSet.getString("name")).append("\n");
                cardList.append("Damage: ").append(resultSet.getInt("Damage")).append("\n");
                cardList.append("xx-------------------------xx\n\n\n\n");
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cardList.toString();
    }
}

