import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import server.repositories.DeckRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

class DeckRepositoryTest {

    @Test
    void testCheckConfiguration_WhenDeckIsNotSet_ShouldReturnFalse() {
        DeckRepository deckRepository = Mockito.mock(DeckRepository.class);
        Mockito.when(deckRepository.checkConfiguration("kienboec")).thenReturn(false);

        assertFalse(deckRepository.checkConfiguration("kienboec"));
    }

    @Test
    void testCheckConfiguration_WhenDeckIsSet_ShouldReturnTrue() {
        DeckRepository deckRepository = Mockito.mock(DeckRepository.class);
        Mockito.when(deckRepository.checkConfiguration("kienboec")).thenReturn(true);

        assertTrue(deckRepository.checkConfiguration("kienboec"));
    }

    @Test
    void testCheckConfiguration_WhenCardIsSetReady_ShouldReturnTrue() {
        DeckRepository deckRepository = Mockito.mock(DeckRepository.class);
        doNothing().when(deckRepository).setCardReady("d78ad29be293zd82test");

        deckRepository.setCardReady("d78ad29be293zd82test");

        verify(deckRepository).setCardReady("d78ad29be293zd82test");
    }
}