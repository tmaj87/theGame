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

    private Queue<String> players = new ConcurrentLinkedQueue<>();

    private int maxPlayers;
    private SimpMessagingTemplate template;
    private WinnerRepository repository;


    public WebServer(@Value("${max.players:2}") int maxPlayers, SimpMessagingTemplate template, WinnerRepository repository) {
        this.maxPlayers = 3;
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
        feedToInfo(playerId + " won");
        feedToPlayer(playerId, "You win!");
        disconnectAllPlayers();
    }

    private String pickRandomPlayer() {
        Random random = new Random();
        int randomInt = random.nextInt(players.size());
        return getNthPlayer(randomInt);
    }

    private String getNthPlayer(int randomInt) {
        return (String) players.toArray()[randomInt];
    }

    private void disconnectAllPlayers() {
        feedToInfo("bye");
        players.clear();
    }

    public void addUserAndNotify(String playerId) {
        players.add(playerId);
        feedToInfo(playerId + " joined");
        shouldInitGame();
    }

    public void removeUserAndNotify(String playerId) {
        players.remove(playerId);
        feedToInfo(playerId + " left");
    }

    private void feedToInfo(String content) {
        template.convertAndSend("/feed/info", new SimpleMessage(content));
    }

    private void feedToPlayer(String playerId, String content) {
        template.convertAndSend("/feed/" + playerId, new SimpleMessage(content));
    }
}
