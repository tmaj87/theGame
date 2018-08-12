package pl.tmaj;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

@Component
public class NotificationsRunner {

    private final ExecutorService executor = newFixedThreadPool(1);

    public NotificationsRunner(Notifications notifications) {
        executor.execute(notifications);
    }

    public void stop() {
        executor.shutdown();
    }
}
