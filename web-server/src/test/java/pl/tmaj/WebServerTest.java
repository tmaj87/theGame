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

    private final UsersHandler users = mock(UsersHandler.class);
    private final UsersNotifier notifier = mock(UsersNotifier.class);
    private final WinnerRepository repository = mock(WinnerRepository.class);
    private final WebServer server = new WebServer(repository, users, notifier);

    @BeforeEach
    void setUp() {
        when(users.count()).thenReturn(ONE);
        when(users.pickRandomUser()).thenReturn(USER);
    }

    @Test
    void shouldWaitIfNotFullGame() {
        setMaxPlayersToSomeValue();

        server.ping();

        verify(repository, never()).save(WINNER);
    }

    @Test
    void shouldSaveAndNotifyAboutWinner() {
        server.ping();

        verify(repository).save(WINNER);
        verify(notifier).notifyWon(WINNER.getName());
    }

    @Test
    void shouldClearPlayerPoolAfterGame() {
        server.ping();

        verify(users).clear();
    }

    private void setMaxPlayersToSomeValue() {
        ReflectionTestUtils.setField(server, "maxPlayers", SOME_VALUE);
    }
}
