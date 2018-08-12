package pl.tmaj;

import org.springframework.stereotype.Component;

@Component
public class Notifications implements Runnable {

    private static final int THREE_SECONDS = 3_000;

    private final WebServer server;
    private final UsersHandler users;
    private final UsersNotifier notifier;
    private boolean loop = true;

    public Notifications(WebServer server, UsersHandler users, UsersNotifier notifier) {
        this.server = server;
        this.users = users;
        this.notifier = notifier;
    }

    @Override
    public void run() {
        while (loop) {
            int missing = server.getMaxPlayers() - users.count();
            notifier.notifyCount(missing);
            server.ping();
            try {
                Thread.sleep(THREE_SECONDS);
            } catch (InterruptedException e) {
                loop = false;
            }
        }
    }
}