package pl.tmaj;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import pl.tmaj.common.SimpleMessage;
import pl.tmaj.common.Winner;
import pl.tmaj.common.WinnerRepository;

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class WebServer {

    private static final String JOINED = " joined";
    private static final String LEFT = " left";
    private static final String BYE = "bye";
    private static final String YOU_WIN = "You win!";
    private static final String WON = " won";
    private int maxPlayers;

    private Queue<String> players = new ConcurrentLinkedQueue<>();

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
        feedToPlayer(playerId, YOU_WIN);
        feedToInfo(playerId + WON);
        disconnectAllPlayers();
    }

    private String pickRandomPlayer() {
        Random random = new Random();
        int randomInt = random.nextInt(players.size());
        return getNthPlayerId(randomInt);
    }

    private String getNthPlayerId(int randomInt) {
        return (String) players.toArray()[randomInt];
    }

    private void disconnectAllPlayers() {
        feedToInfo(BYE);
        players.clear();
    }

    public void addUserAndNotify(String playerId) {
        players.add(playerId);
        feedToInfo(playerId + JOINED);
        shouldInitGame();
    }

    public void removeUserAndNotify(String playerId) {
        players.remove(playerId);
        feedToInfo(playerId + LEFT);
    }

    private void feedToInfo(String content) {
        template.convertAndSend("/feed/info", new SimpleMessage(content));
    }

    private void feedToPlayer(String playerId, String content) {
        template.convertAndSend("/feed/" + playerId, new SimpleMessage(content));
    }
}
