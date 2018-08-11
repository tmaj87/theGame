package pl.tmaj;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
@RefreshScope
public class NotificationLoop {

    private static final int THREE_SECONDS = 3000;
    private final Logger logger = getLogger(WebServer.class);

    private final UsersHandler users;
    private final UsersNotifier notifier;

    @Value("${max.players:1}")
    protected int maxPlayers;

    public NotificationLoop(UsersHandler users, UsersNotifier notifier) {
        this.users = users;
        this.notifier = notifier;
        run();
    }

    public void run() {
        boolean loop = true;
        while (loop) {
            int count = maxPlayers - users.count();
            notifier.notifyCount(count);
            try {
                Thread.sleep(THREE_SECONDS);
            } catch (InterruptedException e) {
                logger.error(e.getMessage());
                loop = false;
            }
        }
    }
}
