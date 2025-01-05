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

}
