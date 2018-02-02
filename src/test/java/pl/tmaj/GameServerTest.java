package pl.tmaj;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.tmaj.common.TestConstants.*;
import static pl.tmaj.common.TestUtils.*;

public class GameServerTest {

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
    void shouldListenOnDefaultPort() {
        assertTrue(connectToDefaultPort());
    }

    @Test
    void shouldNotListenOnOtherPort() {
        assertFalse(connectToPort(OTHER_PORT));
    }

    @Test
    void shouldStopListeningAfter16ThPlayer() {
        mockConnections(SIXTEEN_PLAYERS);

        assertFalse(connectToDefaultPort());
    }

    @Test
    void shouldBeListeningBefore16ThPlayer() {
        mockConnections(FIFTEEN_PLAYERS);

        assertTrue(connectToDefaultPort());
    }

}
