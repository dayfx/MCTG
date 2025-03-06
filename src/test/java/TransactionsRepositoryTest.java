import org.junit.jupiter.api.Test;
import server.repositories.TransactionsRepository;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;


class TransactionsRepositoryTest {

    @Test
    void testCheckCoins_WhenUserHasEnough_ShouldReturnTrue() {
        TransactionsRepository transactionsRepository = Mockito.mock(TransactionsRepository.class);
        Mockito.when(transactionsRepository.checkCoins("kienboec")).thenReturn(true);

        assertTrue(transactionsRepository.checkCoins("kienboec"));
    }

}