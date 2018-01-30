package pl.tmaj;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.junit.jupiter.api.Assertions.*;
import static pl.tmaj.common.TestUtils.connectToPort;
import static pl.tmaj.common.TestUtils.getSocket;

class GameServerTest {

    private static final int DEFAULT_PORT = 9191;
    private static final int OTHER_PORT = 7281;
    private static final int MAX_PLAYERS = 16;

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
        connectPlayers(MAX_PLAYERS);
        assertEquals(false, connectToPort(DEFAULT_PORT));
    }

    @Test
    void shouldBeListeningBefore16ThPlayer() {
        connectPlayers(MAX_PLAYERS - 1);
        assertTrue(connectToPort(DEFAULT_PORT));
    }

    @Test
    void shouldReturnPlayerId() throws Exception {
        String playerId = connectPlayer();
        connectPlayers(MAX_PLAYERS - 1);
        assertEquals("PlayerX", playerId);
    }

    private static String connectPlayer() throws Exception {
        Socket socket = getSocket(DEFAULT_PORT);
        ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream()); // exception here
        String string;
        while ((string = (String) inStream.readObject()) != null) {
            return string;
        }
        return null;
    }

    private static void connectPlayers(int number) {
        for (int i = 0; i < number; i++) {
            connectToPort(DEFAULT_PORT);
        }
    }

}
