package pl.tmaj;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import pl.tmaj.common.SimpleMessage;
import pl.tmaj.common.Winner;
import pl.tmaj.common.WinnerRepository;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.slf4j.LoggerFactory.getLogger;
import static pl.tmaj.common.SimpleMessageType.COUNT;
import static pl.tmaj.common.SimpleMessageType.WON;

@Component
@RefreshScope
public class WebServer {

    private static final int THREE_SECONDS = 3000;
    private ExecutorService executor = Executors.newFixedThreadPool(1);
    private Logger logger = getLogger(WebServer.class);
    private int maxPlayers;
    private WinnerRepository repository;
    private WebServerUsers users;

    public WebServer(@Value("${max.players:1}") int maxPlayers, WinnerRepository repository, WebServerUsers users) {
        this.maxPlayers = maxPlayers;
        this.repository = repository;
        this.users = users;
        runPlayerCountNotification();
    }

    private void runPlayerCountNotification() {
        executor.submit(() -> {
            boolean loop = true;
            while (loop) {
                String missingPlayers = String.valueOf(maxPlayers - users.getUsersCount());
                users.notifyAll(new SimpleMessage(missingPlayers, COUNT));
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
        if (users.getUsersCount() >= maxPlayers) {
            pickWinnerAndAnnounce();
        }
    }

    private void pickWinnerAndAnnounce() {
        String user = users.pickRandomUser();
        repository.save(new Winner(user));
        users.notifyAll(new SimpleMessage(user, WON));
        users.clearAllUsers();
    }
}
