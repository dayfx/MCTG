import application.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import server.repositories.SessionsRepository;

import static org.junit.jupiter.api.Assertions.*;

class SessionsRepositoryTest {
    @Test
    void testLoginUser_WhenUsernameAndPasswordAreCorrect_ShouldReturnTrue() {
        SessionsRepository sessionsRepository = Mockito.mock(SessionsRepository.class);
        User user = new User("altenhof", "correctPassword");

        Mockito.when(sessionsRepository.loginUser(user)).thenReturn(true);

        assertTrue(sessionsRepository.loginUser(user));
    }

    @Test
    void testLoginUser_WhenUsernameAndPasswordAreIncorrect_ShouldReturnFalse() {
        SessionsRepository sessionsRepository = Mockito.mock(SessionsRepository.class);
        User user = new User("altenhof", "incorrectPassword");

        Mockito.when(sessionsRepository.loginUser(user)).thenReturn(false);

        assertFalse(sessionsRepository.loginUser(user));
    }

    @Test
    void testLoginUser_WhenUserDoesNotExist_ShouldReturnFalse() {
        SessionsRepository sessionsRepository = Mockito.mock(SessionsRepository.class);
        User user = new User("fakeUser", "weirdPassword");

        Mockito.when(sessionsRepository.loginUser(user)).thenReturn(false);

        assertFalse(sessionsRepository.loginUser(user));
    }

    @Test
    void testLoginUser_WhenUserExists_ShouldReturnTrue() {
        SessionsRepository sessionsRepository = Mockito.mock(SessionsRepository.class);
        User user = new User("realUser", "correctPassword");

        Mockito.when(sessionsRepository.loginUser(user)).thenReturn(true);

        assertTrue(sessionsRepository.loginUser(user));
    }


}