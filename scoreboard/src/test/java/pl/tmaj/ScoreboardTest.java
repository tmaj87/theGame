package pl.tmaj;

import org.junit.Before;
import org.junit.Test;
import org.springframework.hateoas.Resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScoreboardTest {

    private static final String DUMMY_PLAYER = "dummyPlayer";
    private static final String ANOTHER_DUMMY_PLAYER = "anotherDummyPlayer";

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
        addWinner(DUMMY_PLAYER);
        addWinner(ANOTHER_DUMMY_PLAYER);

        String latest = scoreboard.getBestPlayer();

        assertEquals(ANOTHER_DUMMY_PLAYER, latest);
    }

    @Test
    public void shouldGetSortedPlayersByPoints() {
        addWinner(DUMMY_PLAYER);
        addWinner(DUMMY_PLAYER);
        addWinner(ANOTHER_DUMMY_PLAYER);

        Map<String, Integer> sorted = scoreboard.getSortedBestPlayers();

        assertEquals(2, sorted.size());
        assertEquals(2, sorted.get(DUMMY_PLAYER));
        assertEquals(1, sorted.get(ANOTHER_DUMMY_PLAYER));
    }

    private void addWinner(String name) {
        winners.add(new Winner(name, getLatestId()));
    }

    private Long getLatestId() {
        Long lastId;
        if (winners.size() == 0) {
            lastId = 1L;
        } else {
            lastId = winners.get(winners.size() - 1).getId();
            lastId++;
        }
        return lastId;
    }
}
