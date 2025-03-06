package server.repositories;

import server.server.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeckRepository {

    DatabaseConnection databaseConnection = new DatabaseConnection();

    public void setCardReady(String cardID) {
        try {
            Connection connection = databaseConnection.connect();

            String query = "UPDATE cards SET ready = true WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, cardID);

            preparedStatement.execute();

            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String displayDeck(String user) {
        StringBuilder deck = new StringBuilder(); // to build one singular string

        try {
            Connection connection = databaseConnection.connect();

            String query = "SELECT * FROM cards WHERE owner = ? AND ready = true";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, user);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){ // turning everything into one string
                deck.append("Battle-Ready card:\n");
                deck.append("ID: ").append(resultSet.getString("id")).append("\n");
                deck.append("Name: ").append(resultSet.getString("name")).append("\n");
                deck.append("Damage: ").append(resultSet.getString("damage")).append("\n\n");
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deck.toString();
    }

    public String displayDeckPlain(String user) {
        StringBuilder deck = new StringBuilder(); // to build one singular string

        try {
            Connection connection = databaseConnection.connect();

            String query = "SELECT * FROM cards WHERE owner = ? AND ready = true";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, user);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){ // turning everything into one string
                deck.append("xx--------Battle-Ready Card--------xx\n");
                deck.append("xx---Name: ").append(resultSet.getString("name")).append(" ---xx\n");
                deck.append("xx---Damage: ").append(resultSet.getString("damage")).append(" ---xx\n\n\n");
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deck.toString();
    }

    public boolean checkConfiguration(String user) {
        boolean hasReadyCard = false;

        try {
            Connection connection = databaseConnection.connect();

            String query = "SELECT 1 FROM cards WHERE owner = ? AND ready = true LIMIT 1";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, user);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                hasReadyCard = true;// A ready card exists
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasReadyCard;
    }
}
