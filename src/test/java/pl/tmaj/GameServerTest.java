package pl.tmaj;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameServerTest {

    private static final String HOST = "localhost";
    private static final int DEFAULT_PORT = 9191;

    private ExecutorService executorService;
    private GameServer gameServer;

    @BeforeEach
    void before() {
        executorService = newFixedThreadPool(1);
        gameServer = new GameServer();
        executorService.submit(gameServer);
    }

    @AfterEach
    void after() {
        gameServer.exit();
        executorService.shutdownNow();
    }

    @Test
    void shouldListenOnDefaultPort() {
        assertTrue(isGamePortOpen());
    }

    @Test
    void shouldNotListenOnOtherPort() {
        assertFalse(isGamePortOpen(8181));
    }

    @Test
    void shouldStopListeningAfter16ThPlayer() {
        connect16Players();
        assertFalse(isGamePortOpen());
    }

    @Test
    void shouldGenerateUniqueIdForEveryPlayer() {
        assertTrue(null);
    }

    private static void connect16Players() {
        for (int i = 0; i < 16; i++) {
            isGamePortOpen();
        }
    }

    private static boolean isGamePortOpen() {
        return isGamePortOpen(DEFAULT_PORT);
    }

    private static boolean isGamePortOpen(int port) {
        try (Socket socket = new Socket(HOST, port)) {
            socket.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
