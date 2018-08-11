package pl.tmaj;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import pl.tmaj.common.Winner;
import pl.tmaj.common.WinnerRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.slf4j.LoggerFactory.getLogger;

@Component
@RefreshScope
public class WebServer {

    private static final int THREE_SECONDS = 3000;
    private ExecutorService executor = Executors.newFixedThreadPool(1);
    private Logger logger = getLogger(WebServer.class);
    private int maxPlayers;
    private WinnerRepository repository;
    private WebServerUsers users;
    private UserNotifier notifier;

    public WebServer(@Value("${max.players:1}") int maxPlayers, WinnerRepository repository, WebServerUsers users, UserNotifier notifier) {
        this.maxPlayers = maxPlayers;
        this.repository = repository;
        this.users = users;
        this.notifier = notifier;
        runPlayerCountNotification();
    }

    private void runPlayerCountNotification() {
        executor.submit(() -> {
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
        });
    }

    public void checkPlayerCount() {
        if (users.count() >= maxPlayers) {
            pickWinnerAndAnnounce();
        }
    }

    private void pickWinnerAndAnnounce() {
        String user = users.pickRandomUser().getNick();
        repository.save(new Winner(user));
        notifier.notifyWon(user);
        users.clear();
    }
}
