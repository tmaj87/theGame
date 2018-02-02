package pl.tmaj;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.tmaj.common.TestConstants.DEFAULT_PORT;
import static pl.tmaj.common.TestConstants.PLAYER_ID;
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
        String playerId = new PlayerHandler(getSocket(DEFAULT_PORT)).getId();
        assertTrue(PLAYER_ID.equals(playerId));
    }

}
