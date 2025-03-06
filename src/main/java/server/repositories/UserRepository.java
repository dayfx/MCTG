package server.repositories;

import application.User;
import server.server.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    DatabaseConnection databaseConnection = new DatabaseConnection();

    public boolean checkUser(User user){

        try {
            Connection connection = databaseConnection.connect();
            String query = "SELECT COUNT(*) FROM users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return resultSet.getInt(1) > 0;
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public String displayUserData(String username){

        StringBuilder userData = new StringBuilder(); // to build one singular string

        try {
            Connection connection = databaseConnection.connect();

            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){ //turning everything into one string
                userData.append("User Data:\n");
                userData.append("ID: ").append(resultSet.getInt("id")).append("\n");
                userData.append("Username: ").append(resultSet.getString("username")).append("\n");
                userData.append("Password: ").append(resultSet.getString("password")).append("\n");
                userData.append("Coins: ").append(resultSet.getInt("coins")).append("\n");
                userData.append("Token: ").append(resultSet.getString("token")).append("\n");
                userData.append("Name: ").append(resultSet.getString("name")).append("\n");
                userData.append("Bio: ").append(resultSet.getString("bio")).append("\n");
                userData.append("Image: ").append(resultSet.getString("image")).append("\n");
            }

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userData.toString();
    }


    public void createUser(User user) {

        try {
            Connection connection = databaseConnection.connect();
            String query = "INSERT INTO users (username, password, coins) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, user.getUsername() );
            preparedStatement.setString(2, user.getPassword() );
            preparedStatement.setInt(3, 20);

            preparedStatement.execute();

            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(User user, String extractedName){
        try {
            Connection connection = databaseConnection.connect();
            String query = "UPDATE users SET name = ?, bio = ?, image = ? WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getBio());
            preparedStatement.setString(3, user.getImage());
            preparedStatement.setString(4, extractedName);

            preparedStatement.execute();

            connection.close();

            System.out.println("User updated successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

