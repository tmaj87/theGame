package pl.tmaj;

import org.springframework.stereotype.Component;

@Component
public class Notifications implements Runnable {

    private static final int THREE_SECONDS = 3_000;

    private final WebServer server;
    private final UsersNotifier notifier;
    private final Sleeper sleeper;
    private boolean loop = true;

    public Notifications(WebServer server, UsersNotifier notifier, Sleeper sleeper) {
        this.server = server;
        this.notifier = notifier;
        this.sleeper = sleeper;
    }

    @Override
    public void run() {
        while (loop) {
            int missing = server.getMissingPlayers();
            notifier.notifyCount(missing);
            server.ping();
            try {
                sleeper.sleep(THREE_SECONDS);
            } catch (InterruptedException e) {
                loop = false;
            }
        }
    }

    public boolean isRunning() {
        return loop;
    }
}