import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import server.server.DatabaseConnection;

import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;

class DatabaseConnectionTest {
    @Test
    void testConnect_ShouldThrowSQLException_WhenConnectionFails() throws SQLException {

        DatabaseConnection databaseConnection = new DatabaseConnection();

        try (MockedStatic<DriverManager> driverManagerMock = mockStatic(DriverManager.class)) {
            driverManagerMock.when(() -> DriverManager.getConnection(anyString())).thenThrow(SQLException.class);

            assertThrows(SQLException.class, () -> databaseConnection.connect());
        }
    }
}