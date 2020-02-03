package pl.tmaj;

import org.junit.Before;
import org.junit.Test;
import org.springframework.hateoas.Resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScoreboardTest {

    private static final String DUMMY_PLAYER = "dummyPlayer";
    private static final String ANOTHER_DUMMY_PLAYER = "anotherDummyPlayer";
    private static final Winner DUMMY_WINNER = new Winner(DUMMY_PLAYER);

    private final WinnerRepository repository = mock(WinnerRepository.class);
    private Scoreboard scoreboard = new Scoreboard(repository);
    private List<Winner> winners;

    @Before
    public void setUp() {
        winners = new ArrayList<>();
        Resources latestResources = mock(Resources.class);
        when(repository.getLatest()).thenReturn(latestResources);
        when(latestResources.getContent()).thenReturn(singletonList(DUMMY_WINNER));
        Resources allResources = mock(Resources.class);
        when(repository.findAll()).thenReturn(allResources);
        when(allResources.getContent()).thenReturn(winners);
    }

    @Test
    public void shouldGetLatestWinner() {
        String best = scoreboard.getBestPlayer();
        assertEquals(DUMMY_PLAYER, best);
    }

    @Test
    public void shouldGetSortedPlayersByPoints() {
        addWinners(asList(DUMMY_PLAYER, DUMMY_PLAYER, ANOTHER_DUMMY_PLAYER));
        Map<String, Long> sorted = scoreboard.getSortedPlayers();
        assertEquals(2, sorted.size());
        assertEquals(2, (long) sorted.get(DUMMY_PLAYER));
        assertEquals(1, (long) sorted.get(ANOTHER_DUMMY_PLAYER));
    }

    private void addWinners(List<String> list) {
        Long latest = getLatestId();
        for (String name : list) {
            winners.add(new Winner(name, ++latest));
        }
    }

    private Long getLatestId() {
        Long lastId;
        if (winners.size() == 0) {
            lastId = 1L;
        } else {
            lastId = winners.get(winners.size() - 1).getId();
        }
        return lastId;
    }
}
