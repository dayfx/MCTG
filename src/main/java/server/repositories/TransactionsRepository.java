package server.repositories;

import application.Card;
import server.server.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionsRepository {

    DatabaseConnection databaseConnection = new DatabaseConnection();

    public void purchaseCards(int randomPackage, String purchaser) {
        // Step 1: Select a random packageID

        try {
            Connection connection = databaseConnection.connect();

            String query = "UPDATE cards SET owner = ? WHERE packageid = ?";    // set owner of cards with same packageid to purchaser
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, purchaser);
            preparedStatement.setInt(2, randomPackage);

            preparedStatement.execute();

            String coinsQuery = "UPDATE users SET coins = coins - 5 WHERE username = ?"; // set coins of purchaser to coins - 5
            preparedStatement = connection.prepareStatement(coinsQuery);

            preparedStatement.setString(1, purchaser);

            preparedStatement.executeUpdate();

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getRandomPackageID() {
        int packageID = -1;

        try {
            Connection connection = databaseConnection.connect();

            String query = "SELECT packageid FROM cards WHERE owner IS NULL ORDER BY RANDOM() LIMIT 1"; // select cards with random package id that DO NOT HAVE AN OWNER
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                packageID = resultSet.getInt("packageid");
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return packageID;
    }

    public boolean checkCoins(String purchaser){
        try {
            Connection connection = databaseConnection.connect();

            String query = "SELECT coins FROM users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, purchaser);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                int coins = resultSet.getInt("coins");
                if (coins >= 5){
                    return true;
                }
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
