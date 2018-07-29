package pl.tmaj;

import org.junit.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.Socket;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
public class PlayerHandlerTest {

    private static final String PLAYER_ID_PATTERN = "\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}";

    @Mock
    private Socket socket;

    @Test
    public void shouldReturnPlayerId() {
        String playerId = newPlayer().getId();

        assertTrue(playerId.matches(PLAYER_ID_PATTERN));
    }

    @Test
    public void shouldReturnDifferentPlayers() {
        PlayerHandler playerOne = newPlayer();
        PlayerHandler playerTwo = newPlayer();

        assertNotEquals(playerOne, playerTwo);
    }

    private PlayerHandler newPlayer() {
        return new PlayerHandler(socket);
    }
}
