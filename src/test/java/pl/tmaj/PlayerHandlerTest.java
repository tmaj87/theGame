package pl.tmaj;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.tmaj.common.TestConstants.*;
import static pl.tmaj.common.TestUtils.getSocket;

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
        assertTrue(PLAYER_ID.equals(playerId));
    }

    @Test
    void shouldReturnDifferentIdForEveryPlayer() {
        Set<String> playerIds = new HashSet<>();
        for (int i = 0; i < SIXTEEN_PLAYERS; i++) {
            playerIds.add(getNewPlayerId());
        }

        assertEquals(playerIds.size(), SIXTEEN_PLAYERS);
    }

    private String getNewPlayerId() {
        return new PlayerHandler(getSocket(DEFAULT_PORT)).getId();
    }

}
