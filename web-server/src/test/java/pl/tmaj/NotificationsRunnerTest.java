package pl.tmaj;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.ReflectionTestUtils.setField;

public class NotificationsRunnerTest {

    private final Notifications notifications = mock(Notifications.class);
    private final ExecutorService executor = mock(ExecutorService.class);

    private NotificationsRunner runner = new NotificationsRunner(notifications);

    @BeforeEach
    void injectMocks() {
        setField(runner, "executor", executor);
    }

    @Test
    void shouldShutdownNotifications() {
        runner.stop();

        verify(executor).shutdown();
    }

}
