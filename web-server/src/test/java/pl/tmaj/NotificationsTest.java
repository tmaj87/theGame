package pl.tmaj;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class NotificationsTest {

    private ExecutorService executor = newFixedThreadPool(1);
    private WebServer server;
    private UsersNotifier notifier;
    private Sleeper sleeper;
    private Notifications notifications;

    @BeforeEach
    void setUp() {
        server = mock(WebServer.class);
        notifier = mock(UsersNotifier.class);
        sleeper = mock(Sleeper.class);
        notifications = new Notifications(server, notifier, sleeper);
        executor.execute(notifications);
    }

    @AfterEach
    void tearDown() {
        executor.shutdown();
    }

    @Test
    void shouldAskForMissingPlayers() {
        verify(server).getMissingPlayers();
    }

    @Test
    void shouldNotifyAboutMissingPlayers() {
        verify(notifier, atLeastOnce()).notifyCount(anyInt());
    }

    @Test
    void shouldSleepBetweenNotifications() throws Exception {
        verify(sleeper, atLeastOnce()).sleep(anyInt());
    }

    @Test
    void shouldStopAfterInterruptedException() throws Exception {
        doThrow(InterruptedException.class).when(sleeper).sleep(anyInt());

        verify(sleeper).sleep(anyInt());

        assertFalse(notifications.isRunning());
    }

    @Test
    void shouldPingWebServer() {
        verify(server, atLeastOnce()).ping();
    }
}
