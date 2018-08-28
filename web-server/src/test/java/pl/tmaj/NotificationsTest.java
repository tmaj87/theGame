package pl.tmaj;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.verification.VerificationMode;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class NotificationsTest {

    private ExecutorService executor;
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
        executor = newFixedThreadPool(1);
        executor.execute(notifications);
    }

    @AfterEach
    void tearDown() {
        notifications.stop();
        executor.shutdown();
    }

    @Test
    void shouldAskForMissingPlayers() {
        verify(server, onceDuringOneSecond()).getMissingPlayers();
    }

    @Test
    void shouldNotifyAboutMissingPlayers() {
        verify(notifier, onceDuringOneSecond()).notifyCount(anyInt());
    }

    @Test
    void shouldSleepBetweenNotifications() throws Exception {
        verify(sleeper, onceDuringOneSecond()).sleep(anyInt());
    }

    @Test
    void shouldStopAfterInterruptedException() throws Exception {
        doThrow(InterruptedException.class).when(sleeper).sleep(anyInt());

        verify(sleeper, onceDuringOneSecond()).sleep(anyInt());

        assertFalse(notifications.isRunning());
    }

    @Test
    void shouldPingWebServer() {
        verify(server, onceDuringOneSecond()).ping();
    }

    private VerificationMode onceDuringOneSecond() {
        return timeout(1_000).atLeast(1);
    }
}
