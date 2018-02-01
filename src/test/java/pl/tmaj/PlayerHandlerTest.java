package pl.tmaj;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.tmaj.common.TestConstants.*;
import static pl.tmaj.common.TestUtils.getSocket;
import static pl.tmaj.common.TestUtils.mockConnections;

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
    void shouldReturnPlayerId() throws Exception {
        mockConnections(FIFTEEN_PLAYERS);
        String playerId = getPlayerId();
        assertTrue(PLAYER_ID.equals(playerId));
    }

    private String getPlayerId() throws Exception {
        Socket socket = getSocket(DEFAULT_PORT);
        return getMessageFromSocket(socket);
    }

    private String getMessageFromSocket(Socket socket) throws IOException, ClassNotFoundException {
        ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
        String string;
        while ((string = (String) inStream.readObject()) != null) {
            return string;
        }
        return null;
    }

}
