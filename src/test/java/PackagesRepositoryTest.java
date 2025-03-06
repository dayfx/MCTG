import application.Card;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import server.repositories.PackagesRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PackagesRepositoryTest {
    @Test
    void testCreateCard_ShouldNotThrowException() {

        PackagesRepository packagesRepository = Mockito.mock(PackagesRepository.class);

        List<Card> testCards = Arrays.asList(
                new Card("card1id", "Dragon", 50.0, 1),
                new Card("card2id", "Phoenix", 45.0, 1)
        );

        doNothing().when(packagesRepository).createCard(testCards);

        packagesRepository.createCard(testCards);

        verify(packagesRepository).createCard(testCards);
    }

    @Test
    void testCreateCard_ShouldThrowException_WhenDatabaseFails() {

        PackagesRepository packagesRepository = Mockito.mock(PackagesRepository.class);

        List<Card> testCards = Arrays.asList(
                new Card("card1id", "Dragon", 50.0, 1),
                new Card("card2id", "Phoenix", 45.0, 1)
        );

        doThrow(new RuntimeException("Database error")).when(packagesRepository).createCard(testCards);

        assertThrows(RuntimeException.class, () -> packagesRepository.createCard(testCards));
    }

    @Test
    void testGetNextPackageId_ShouldReturnNextId() {

        PackagesRepository packagesRepository = Mockito.mock(PackagesRepository.class);

        when(packagesRepository.getNextPackageId()).thenReturn(6); // next packageid supposed to be 6

        int nextId = packagesRepository.getNextPackageId();

        assertEquals(6, nextId); // check if 6
    }

    @Test
    void testGetNextPackageId_ShouldThrowException_WhenPackageIDNotCorrect() {

        PackagesRepository packagesRepository = Mockito.mock(PackagesRepository.class);

        doThrow(new RuntimeException("Something went wrong with the PackageID")).when(packagesRepository).getNextPackageId();

        assertThrows(RuntimeException.class, packagesRepository::getNextPackageId); // make sure exception's thrown
    }
}
