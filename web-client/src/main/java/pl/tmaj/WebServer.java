package pl.tmaj;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import pl.tmaj.common.SimpleMessage;
import pl.tmaj.common.Winner;
import pl.tmaj.common.WinnerRepository;

import java.util.Iterator;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Stream;

@Component
public class WebServer {

    private static final String DEFAULT_PLAYER_NAME = "";
    private Queue<String> players = new ConcurrentLinkedQueue<>();
    private int maxPlayers;
    private SimpMessagingTemplate template;
    private WinnerRepository repository;


    public WebServer(@Value("${max.players:2}") int maxPlayers, SimpMessagingTemplate template, WinnerRepository repository) {
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
        return getNthPlayer(randomInt);
    }

    private String getNthPlayer(int randomInt) {
        for (Iterator<String> it = players.iterator().; it.hasNext(); ) {
            String player = it.next();
        }
        for(int i = 0; i < randomInt; i++)
            current = iterator.next();
        Stream.of(players.iterator()).
        return null;
    }

    private void disconnectAllPlayers() {
        feedInfo("Bye");
        players.clear();
    }

    public void addUserAndNotify(String playerId) {
        players.add(playerId);
        feedInfo(playerId + " joined");
        shouldInitGame();
    }

    public void removeUserAndNotify(String playerId) {
        players.remove(playerId);
        feedInfo(playerId + " left");
    }

    private void feedInfo(String content) {
        template.convertAndSend("/feed/info", new SimpleMessage(content));
    }
}
