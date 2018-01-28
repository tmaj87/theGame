package pl.tmaj;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.tmaj.common.TestUtils.connectToPort;

class GameServerTest {

    private static final int DEFAULT_PORT = 9191;
    private static final int OTHER_PORT = 7281;
    private static final int MAX_PLAYERS = 16;

    private ExecutorService gameSimulation;

    @BeforeEach
    void beforeEach() {
        gameSimulation = newFixedThreadPool(1);
        gameSimulation.submit(new GameServer());
    }

    @AfterEach
    void afterEach() {
        gameSimulation.shutdownNow();
    }

    @Test
    void shouldListenOnDefaultPort() {
        assertTrue(connectToPort(DEFAULT_PORT));
    }

    @Test
    void shouldNotListenOnOtherPort() {
        assertFalse(connectToPort(OTHER_PORT));
    }

    @Test
    void shouldStopListeningAfter16ThPlayer() {
        connectPlayers(MAX_PLAYERS);
        assertFalse(connectToPort(DEFAULT_PORT));
    }

    @Test
    void shouldBeListeningBefore16ThPlayer() {
        connectPlayers(MAX_PLAYERS - 1);
        assertTrue(connectToPort(DEFAULT_PORT));
    }

    @Test
    void shouldGenerateUniqueIdForEveryPlayer() {
        String player1 = "";
        String player2 = "";
        assertFalse(player1.equals(player2));
    }

    private static void connectPlayers(int number) {
        for (int i = 0; i < number; i++) {
            connectToPort(DEFAULT_PORT);
        }
    }

}
