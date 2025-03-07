import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import server.repositories.BattlesRepository;

import static org.junit.jupiter.api.Assertions.*;

class BattlesRepositoryTest {
    @Test
    void testExecuteBattle_ShouldReturnNonEmptyLog() {
        BattlesRepository battlesRepository = Mockito.mock(BattlesRepository.class);
        Mockito.when(battlesRepository.executeBattle("player1", "player2")).thenReturn("Battle begins between player1 and player2");

        String battleLog = battlesRepository.executeBattle("player1", "player2");

        assertNotNull(battleLog);
        assertFalse(battleLog.isEmpty());
    }

    @Test
    void testChaosBurstChance_IsWithinValidPercent() {
        BattlesRepository battlesRepository = new BattlesRepository();

        for (int i = 0; i < 30; i++) { // run 30 battle simulations
            String battleLog = battlesRepository.executeBattle("kienboec", "altenhof");

            // split battle log into single lines and find the one that contains "CHAOS BURST chance"
            String chaosBurstLine = null;

            for (String line : battleLog.split("\n")) {
                if (line.contains("CHAOS BURST chance for this battle:")){
                    chaosBurstLine = line;
                    break;
                }
            }
            assertNotNull(chaosBurstLine, "CHAOS BURST chance should be present in the battle log");

            // get number by splitting at ":" - remove "%" and parse
            String percentageString = chaosBurstLine.split(":")[1].trim(); // get everything after ":"
            percentageString = percentageString.split("%")[0].trim(); // get everything before "%"

            percentageString = percentageString.replace(',', '.'); // replace comma with dot cuz the parsing doesn't like the comma

            double chaosChance = Double.parseDouble(percentageString); // turn string into double

            // check if chaos burst chance is between 10% ~ 30%
            assertTrue(chaosChance >= 10.0 && chaosChance <= 30.0,
                    "CHAOS BURST chance should be between 10% ~ 30% : " + chaosChance + "%");
        }
    }
}