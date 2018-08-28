package pl.tmaj;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.verification.VerificationMode;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.awaitility.Awaitility.await;
import static org.awaitility.Duration.ONE_SECOND;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class NotificationsTest {

    private final WebServer server = mock(WebServer.class);
    private final UsersNotifier notifier = mock(UsersNotifier.class);
    private final Sleeper sleeper = mock(Sleeper.class);

    private ExecutorService executor;
    private Notifications notifications;

    @BeforeEach
    void setUp() {
        executor = newFixedThreadPool(1);
        notifications = new Notifications(server, notifier, sleeper);
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
    void shouldStopAfterInterruptedExceptionWithinOneSecond() throws Exception {
        doThrow(InterruptedException.class).when(sleeper).sleep(anyInt());

        verify(sleeper, onceDuringOneSecond()).sleep(anyInt());

        await().atMost(ONE_SECOND).until(() -> !notifications.isRunning());
    }

    @Test
    void shouldPingWebServer() {
        verify(server, onceDuringOneSecond()).ping();
    }

    private VerificationMode onceDuringOneSecond() {
        return timeout(1_000).atLeast(1);
    }
}
