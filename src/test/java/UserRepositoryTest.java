import application.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import server.repositories.UserRepository;
import server.server.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRepositoryTest {
    @Test
    void testCheckUser_ShouldReturnTrue_WhenUserExists() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User testUser = new User("testKienboec", "testPassword");

        when(userRepository.checkUser(testUser)).thenReturn(true);

        boolean userExists = userRepository.checkUser(testUser);

        assertTrue(userExists);
    }

    @Test
    void testCheckUser_ShouldReturnFalse_WhenUserDoesNotExist() {

        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User testUser = new User("unkownUser", "testPassword");

        when(userRepository.checkUser(testUser)).thenReturn(false);

        boolean userExists = userRepository.checkUser(testUser);

        assertFalse(userExists);
    }

    @Test
    void testCreateUser_ShouldNotThrowException_WhenUserIsCreatedSuccessfully() {

        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User testUser = new User("testKienboec", "testPassword");

        doNothing().when(userRepository).createUser(testUser);

        userRepository.createUser(testUser);

        verify(userRepository, times(1)).createUser(testUser);
    }

    @Test
    void testUpdateUser_ShouldNotThrowException_WhenUserIsUpdatedSuccessfully() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);

        User testUser = new User("altenhof", "password");
        String extractedName = "altenhof";

        doNothing().when(userRepository).updateUser(testUser, extractedName);

        userRepository.updateUser(testUser, extractedName);

        verify(userRepository, times(1)).updateUser(testUser, extractedName);
    }
}