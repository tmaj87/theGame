package pl.tmaj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static pl.tmaj.common.TestConstants.PLAYER_ID_PATTERN;
import static pl.tmaj.common.TestConstants.SIXTEEN_PLAYERS;
import static pl.tmaj.common.TestUtils.getNewPlayerId;
import static pl.tmaj.common.TestUtils.getSetOfSixteenPlayers;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlayerHandlerTest {

    // new PlayerHandler( [socket mock] )

    @Test
    public void shouldReturnPlayerId() {
        String playerId = getNewPlayerId();

        assertTrue(playerId.matches(PLAYER_ID_PATTERN));
    }

    @Test
    public void shouldReturnDifferentIdForEveryPlayer() {
        Set<String> playerIds = getSetOfSixteenPlayers();

        assertEquals(playerIds.size(), SIXTEEN_PLAYERS);
    }

}
