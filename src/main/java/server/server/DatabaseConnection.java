package server.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private final String connectionString = "jdbc:postgresql://localhost:5432/postgres?user=danielfialafh&password=mklnjkmklnjk2K";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(connectionString);
    }
}
