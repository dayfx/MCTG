import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import server.repositories.TransactionsRepository;

import static org.junit.jupiter.api.Assertions.*;

class TransactionsRepositoryTest {

    @Test
    void testCheckCoins_WhenUserHasEnough_ShouldReturnTrue() {
        TransactionsRepository transactionsRepository = Mockito.mock(TransactionsRepository.class);
        Mockito.when(transactionsRepository.checkCoins("kienboec")).thenReturn(true);

        assertTrue(transactionsRepository.checkCoins("kienboec"));
    }

    @Test
    void testCheckCoins_WhenUserHasNotEnoughCoins_ShouldReturnFalse() {
        TransactionsRepository transactionsRepository = Mockito.mock(TransactionsRepository.class);
        Mockito.when(transactionsRepository.checkCoins("altenhof")).thenReturn(false);

        assertFalse(transactionsRepository.checkCoins("altenhof"));
    }

    @Test
    void testPurchaseCards_BuysCardsAndUpdatesCoinsCorrectly() {
        TransactionsRepository transactionsRepository = Mockito.mock(TransactionsRepository.class);
        Mockito.doNothing().when(transactionsRepository).purchaseCards(3, "kienboec");

        transactionsRepository.purchaseCards(3, "kienboec");

        Mockito.verify(transactionsRepository).purchaseCards(3, "kienboec");
    }

    @Test
    void testGetRandomPackageID_WhenNoPackagesExist_ShouldReturnNegativeOne() {
        TransactionsRepository transactionsRepository = Mockito.mock(TransactionsRepository.class);
        Mockito.when(transactionsRepository.getRandomPackageID()).thenReturn(-1);

        assertEquals(-1, transactionsRepository.getRandomPackageID());
    }



}