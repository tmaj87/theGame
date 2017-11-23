package pl.tmaj;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GameServerTest {

    public static final GameServer GAME_SERVER = new GameServer();

    @Test
    void shouldListenOnPort9191() throws IOException {
        assertTrue(isListeningOn(9191));
    }

    @Test
    void shouldReleaseGateFor16Players() {
        assertTrue(null);
    }

    private static boolean isListeningOn(int port) {
        try(Socket socket = new Socket("localhost", port)) {
            socket.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
