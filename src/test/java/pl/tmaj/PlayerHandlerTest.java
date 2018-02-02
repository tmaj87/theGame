package pl.tmaj;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.tmaj.common.TestConstants.PLAYER_ID_PATTERN;
import static pl.tmaj.common.TestConstants.SIXTEEN_PLAYERS;
import static pl.tmaj.common.TestUtils.getSetOfSixteenPlayers;
import static pl.tmaj.common.TestUtils.getNewPlayerId;

public class PlayerHandlerTest {

    private TheGame gameSimulation;

    @BeforeEach
    void beforeEach() {
        gameSimulation = new TheGame();
    }

    @AfterEach
    void afterEach() throws IOException {
        gameSimulation.stop();
    }

    @Test
    void shouldReturnPlayerId() {
        String playerId = getNewPlayerId();

        assertTrue(playerId.matches(PLAYER_ID_PATTERN));
    }

    @Test
    void shouldReturnDifferentIdForEveryPlayer() {
        Set<String> playerIds = getSetOfSixteenPlayers();

        assertEquals(playerIds.size(), SIXTEEN_PLAYERS);
    }

}
