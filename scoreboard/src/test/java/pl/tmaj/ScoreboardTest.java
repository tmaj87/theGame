package pl.tmaj;

import org.junit.Before;
import org.junit.Test;
import org.springframework.hateoas.Resources;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScoreboardTest {

    private static final String DUMMY_PLAYER = "DUMMY_PLAYER";
    private static final String DUMMY_PLAYER_1 = DUMMY_PLAYER + "_1";
    private static final String DUMMY_PLAYER_2 = DUMMY_PLAYER + "_2";
    private static final String DUMMY_PLAYER_3 = DUMMY_PLAYER + "_3";

    private final WinnerRepository repository = mock(WinnerRepository.class);
    private final Resources resources = mock(Resources.class);
    private List<Winner> winners;
    private Scoreboard scoreboard = new Scoreboard(repository);

    @Before
    public void setUp() {
        winners = new ArrayList<>();
        when(repository.getLatest()).thenReturn(resources);
        when(resources.getContent()).thenReturn(winners);
    }

    @Test
    public void shouldGetLatestWinner() {
        addWinner(DUMMY_PLAYER_1);
        addWinner(DUMMY_PLAYER_2);
        addWinner(DUMMY_PLAYER_3);
        String latest = scoreboard.getBestPlayer();

        assertEquals(DUMMY_PLAYER, latest);
    }

    @Test
    public void shouldGetSortedPlayersByPoints() {
        addWinner(DUMMY_PLAYER_1);
        String latest = scoreboard.getBestPlayer();

        assertEquals(DUMMY_PLAYER, latest);
    }

    @Test
    public void shouldGetDefaultContentInCaseOfNoResponse() {

    }

    @Test
    public void shouldGetDefaultContentInCaseOfNoWinners() {

    }

    private void addWinner(String name) {
        winners.add(new Winner(name));
    }
}
