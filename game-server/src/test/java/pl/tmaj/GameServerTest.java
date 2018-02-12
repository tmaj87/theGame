package pl.tmaj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static pl.tmaj.common.TestConstants.*;
import static pl.tmaj.common.TestUtils.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameServerTest {

    @Test
    public void shouldListenOnDefaultPort() {
        assertTrue(connectToDefaultPort());
    }

    @Test
    public void shouldNotListenOnOtherPort() {
        assertFalse(connectToPort(OTHER_PORT));
    }

    @Test
    public void shouldStopListeningAfter16ThPlayer() {
        mockConnections(SIXTEEN_PLAYERS);

        assertFalse(connectToDefaultPort());
    }

    @Test
    public void shouldBeListeningBefore16ThPlayer() {
        mockConnections(FIFTEEN_PLAYERS);

        assertTrue(connectToDefaultPort());
    }

}
