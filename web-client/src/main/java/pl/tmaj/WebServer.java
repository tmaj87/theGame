package pl.tmaj;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import pl.tmaj.common.SimpleMessage;
import pl.tmaj.common.Winner;
import pl.tmaj.common.WinnerRepository;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.slf4j.LoggerFactory.getLogger;
import static pl.tmaj.common.SimpleMessageType.*;

@Component
public class WebServer {

    private static final int THREE_SECONDS = 3000;
    private List<String> players = new CopyOnWriteArrayList<>();
    private ExecutorService executor = Executors.newFixedThreadPool(1);
    private Logger logger = getLogger(WebServer.class);
    private int maxPlayers;
    private SimpMessagingTemplate template;
    private WinnerRepository repository;

    public WebServer(@Value("${max.players:1}") int maxPlayers, SimpMessagingTemplate template, WinnerRepository repository) {
        this.maxPlayers = maxPlayers;
        this.template = template;
        this.repository = repository;
        runPlayerCountNotification();
    }

    private void runPlayerCountNotification() {
        executor.submit(() -> {
            boolean loop = true;
            while (loop) {
                String missingPlayers = String.valueOf(maxPlayers - players.size());
                feedNewMessage(new SimpleMessage(missingPlayers, COUNT));
                try {
                    Thread.sleep(THREE_SECONDS);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                    loop = false;
                }
            }
        });
    }

    public void haveLastPlayerJoined() {
        if (players.size() >= maxPlayers) {
            pickWinner();
        }
    }

    private void pickWinner() {
        String playerId = pickRandomPlayer();
        repository.save(new Winner(playerId));
        feedNewMessage(new SimpleMessage(playerId, WON));
        players.clear();
    }

    private String pickRandomPlayer() {
        Random random = new Random();
        int randomInt = random.nextInt(players.size());
        return players.get(randomInt);
    }

    public void addUser(String playerId) {
        players.add(playerId);
        feedNewMessage(new SimpleMessage(playerId, JOIN));
    }

    public void removeUser(String playerId) {
        players.remove(playerId);
        feedNewMessage(new SimpleMessage(playerId, LEFT));
    }

    private void feedNewMessage(SimpleMessage message) {
        template.convertAndSend("/feed/info", message);
    }
}
