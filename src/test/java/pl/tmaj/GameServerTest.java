package pl.tmaj;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameServerTest {

    private static final int PORT = 9191;
    private ExecutorService gameServer; // ThreadLocal

    @BeforeEach
    void initGame() {
        gameServer = newSingleThreadExecutor();
        gameServer.submit(GameServer::new);
    }

    @AfterEach
    void stopGame() {
        gameServer.shutdown();
    }

    @Test
    void shouldListenOnPort9191() throws IOException {
        assertTrue(isPortOpen(PORT));
    }

    @Test
    void shouldReleaseGateFor16Players() {
        assertTrue(null);
    }

    private static boolean isPortOpen(int port) {
        try (Socket socket = new Socket("localhost", port)) {
            socket.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
