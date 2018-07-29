package pl.tmaj;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.Socket;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SpringBootTest
public class GameServerApplicationTest {

    private static final int OTHER_PORT = 7281;
    private static final int FULL_SERVER = 16;
    private static final String LOCALHOST = "localhost";
    private static final int DEFAULT_PORT = 9191;

    @Test
    public void shouldListenOnDefaultPort() {
        assertTrue(connectToServer());
    }

    @Test
    public void shouldNotListenOnOtherPort() {
        assertFalse(connectToServerOnPort(OTHER_PORT));
    }

    @Test
    public void shouldStopListeningAfter16ThPlayer() {
        multipleConnectionsToServer(FULL_SERVER);

        assertFalse(connectToServer());
    }

    @Test
    public void shouldListenIfNotFull() {
        multipleConnectionsToServer(FULL_SERVER - 1);

        assertTrue(connectToServer());
    }


    private static boolean connectToServer() {
        return getSocket(DEFAULT_PORT) != null;
    }

    private static boolean connectToServerOnPort(int port) {
        return getSocket(port) != null;
    }

    private static void multipleConnectionsToServer(int number) {
        for (int i = 0; i < number; i++) {
            connectToServer();
        }
    }

    private static Socket getSocket(int port) {
        Socket socket;
        try {
            socket = new Socket(LOCALHOST, port);
        } catch (Exception e) {
            socket = null;
        }
        return socket;
    }
}
