package pl.tmaj;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.tmaj.common.TestConstants.*;
import static pl.tmaj.common.TestUtils.getSocket;
import static pl.tmaj.common.TestUtils.mockConnections;

public class PlayerHandlerTest {

    private List<Socket> acquiredConnections = new ArrayList<>();
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
    void shouldReturnPlayerId() throws Exception {
        mockConnections(FIFTEEN_PLAYERS);
        String playerId = connectPlayer();
        assertTrue(PLAYER_ID.equals(playerId));
    }

    private String connectPlayer() throws Exception {
        Socket socket = getSocket(DEFAULT_PORT);
        acquiredConnections.add(socket);
        ObjectInputStream inStream = new ObjectInputStream(socket.getInputStream());
        String string;
        while ((string = (String) inStream.readObject()) != null) {
            return string;
        }
        return null;
    }

}
