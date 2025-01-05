package server.repositories;

import application.User;
import server.server.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionsRepository {

    DatabaseConnection databaseConnection = new DatabaseConnection();

    public boolean loginUser(User user) {

        // check for username and password in db and create token
        try {
            Connection connection = databaseConnection.connect();

            String query = "SELECT password FROM users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Step 2: Verify the password
                String storedPassword = resultSet.getString("password");

                if (storedPassword.equals(user.getPassword())){
                    // Step 3: Generate a new token
                    String generatedToken = user.getUsername() + "-mtcgToken";

                    String updateQuery = "UPDATE users SET token = ? WHERE username = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                    updateStatement.setString(1, generatedToken);
                    updateStatement.setString(2, user.getUsername());

                    updateStatement.execute();

                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
