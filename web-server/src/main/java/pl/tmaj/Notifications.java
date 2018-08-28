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
        try {
            while (loop) {
                notifyAboutMissingPlayers();
                server.ping();
                sleeper.sleep(THREE_SECONDS);
            }
        } catch (InterruptedException e) {
            loop = false;
        }
    }

    private void notifyAboutMissingPlayers() {
        int missing = server.getMissingPlayers();
        notifier.notifyCount(missing);
    }

    public boolean isRunning() {
        return loop;
    }

    public void stop() {
        loop = false;
    }
}