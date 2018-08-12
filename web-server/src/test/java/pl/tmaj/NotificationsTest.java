package pl.tmaj;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class NotificationsTest {

    private final ExecutorService executor = newFixedThreadPool(1);

    private final UsersHandler users = mock(UsersHandler.class);
    private final UsersNotifier notifier = mock(UsersNotifier.class);
    private final WebServer server = mock(WebServer.class);

    private Notifications post;

    @BeforeEach
    void setUp() {
        post =  new Notifications(server, users, notifier);
        executor.execute(post);
    }

    @AfterEach
    void tearDown() {
        executor.shutdown();
    }

    @Test
    void shouldNotifyAboutMissingPlayers() {
        verify(notifier).notifyCount(anyInt());
    }

    @Test
    void shouldSleepBetweenNotifications() {
        fail();
    }

    @Test
    void shouldPingWebServer() {
        verify(server).ping();
    }
}
