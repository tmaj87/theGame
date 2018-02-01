package pl.tmaj;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.tmaj.common.TestConstants.*;
import static pl.tmaj.common.TestUtils.connectToPort;
import static pl.tmaj.common.TestUtils.mockConnections;

public class GameServerTest {

    private static List<Socket> acquiredConnections = new ArrayList<>();

    private ExecutorService gameSimulation;
    private GameServer gameServer;

    @BeforeEach
    void beforeEach() {
        gameSimulation = newFixedThreadPool(1);
        gameServer = new GameServer();
        gameSimulation.submit(gameServer);
    }

    @AfterEach
    void afterEach() throws IOException {
        gameServer.stop();
        gameSimulation.shutdownNow();
        for (Socket socket : acquiredConnections) {
            socket.close();
        }
    }

    @Test
    void shouldListenOnDefaultPort() {
        assertTrue(connectToPort(DEFAULT_PORT));
    }

    @Test
    void shouldNotListenOnOtherPort() {
        assertEquals(false, connectToPort(OTHER_PORT));
    }

    @Test
    void shouldStopListeningAfter16ThPlayer() {
        mockConnections(SIXTEEN_PLAYERS);
        assertEquals(false, connectToPort(DEFAULT_PORT));
    }

    @Test
    void shouldBeListeningBefore16ThPlayer() {
        mockConnections(FIFTEEN_PLAYERS);
        assertTrue(connectToPort(DEFAULT_PORT));
    }

}
