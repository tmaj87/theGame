package pl.tmaj;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import pl.tmaj.common.SimpleMessage;
import pl.tmaj.common.Winner;
import pl.tmaj.common.WinnerRepository;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebServer {

    private static final String DEFAULT_PLAYER_NAME = "";
    private Map<String, String> players = new ConcurrentHashMap<>();
    private int maxPlayers;
    private SimpMessagingTemplate template;
    private WinnerRepository repository;


    public WebServer(@Value("${max.players:1}") int maxPlayers, SimpMessagingTemplate template, WinnerRepository repository) {
        this.maxPlayers = maxPlayers;
        this.template = template;
        this.repository = repository;
    }

    private void shouldInitGame() {
        if (players.size() >= maxPlayers) {
            initGame();
        }
    }

    private void initGame() {
        String playerId = pickRandomPlayer();
        repository.save(new Winner(playerId));
        feedInfo(playerId + " won");
        disconnectAllPlayers();
    }

    private String pickRandomPlayer() {
        Random random = new Random();
        int randomInt = random.nextInt(players.size());
        players.get(randomInt);
        // get players[randomInt]
        return String.valueOf(randomInt);
    }

    private void disconnectAllPlayers() {
        feedInfo("Bye");
        players.clear();
    }

    public void addUser(String playerId) {
        players.put(playerId, DEFAULT_PLAYER_NAME);
        feedInfo(playerId + " joined");
        shouldInitGame();
    }

    public void removeUser(String playerId) {
        players.remove(playerId);
        feedInfo(playerId + " left");
    }

    private void feedInfo(String content) {
        template.convertAndSend("/feed/info", new SimpleMessage(content));
    }
}
