package pl.tmaj;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.tmaj.common.TestConstants.*;
import static pl.tmaj.common.TestUtils.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameServerTest {

    @Autowired private GameServer gameSimulation;

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
