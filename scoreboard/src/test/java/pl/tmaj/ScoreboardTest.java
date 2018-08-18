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
        when(repository.findAll()).thenReturn(resources);
        when(resources.getContent()).thenReturn(winners);
    }

    @Test
    public void shouldGetLatestWinner() {
        // there is no need to test it,
        // REST endpoint "/winners?size=1&sort=id,desc" provides latest winner
    }

    @Test
    public void shouldGetSortedPlayersByPoints() {
        addWinner(DUMMY_PLAYER);
        addWinner(DUMMY_PLAYER);
        addWinner(ANOTHER_DUMMY_PLAYER);

        Map<String, Long> sorted = scoreboard.getSortedPlayers();

        assertEquals(2, sorted.size());
        assertEquals(2, (long) sorted.get(DUMMY_PLAYER));
        assertEquals(1, (long) sorted.get(ANOTHER_DUMMY_PLAYER));
    }

    private void addWinner(String name) {
        Long latest = getLatestId();
        winners.add(new Winner(name, ++latest));
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
