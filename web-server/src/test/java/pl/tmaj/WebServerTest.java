package pl.tmaj;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.*;

public class WebServerTest {

    private static final int ONE = 1;
    private static final String DUMMY_USER = "dummyUser";
    private static final Winner WINNER = new Winner(DUMMY_USER);
    private static final User USER = new User(DUMMY_USER, DUMMY_USER);
    private static final int SOME_VALUE = 14;

    private UsersHandler users = mock(UsersHandler.class);
    private UsersNotifier notifier = mock(UsersNotifier.class);
    private WinnerRepository repository = mock(WinnerRepository.class);
    private WebServer server = new WebServer(repository, users, notifier);

    @BeforeEach
    void setUp() {
        when(users.count()).thenReturn(ONE);
        when(users.pickRandomUser()).thenReturn(USER);
    }

    @Test
    void shouldWaitIfNotFullGame() {
        setMaxPlayersToSomeValue();

        server.checkPlayerCount();

        verify(repository, never()).save(WINNER);
    }

    @Test
    void shouldEvaluateWinner() {
        server.checkPlayerCount();

        verify(repository).save(WINNER);
        verify(notifier).notifyWon(WINNER.getName());
    }

    @Test
    void shouldClearPlayerPoolAfterGame() {
        server.checkPlayerCount();

        verify(users).clear();
    }

    private void setMaxPlayersToSomeValue() {
        ReflectionTestUtils.setField(server, "maxPlayers", SOME_VALUE);
    }
}
