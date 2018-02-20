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

import static pl.tmaj.common.SimpleMessageType.JOIN;
import static pl.tmaj.common.SimpleMessageType.LEFT;
import static pl.tmaj.common.SimpleMessageType.WON;

@Component
public class WebServer {

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
        feedNewMessage(new SimpleMessage(playerId, WON));
        players.clear();
    }

    private String pickRandomPlayer() {
        Random random = new Random();
        int randomInt = random.nextInt(players.size());
        return getNthPlayerId(randomInt);
    }

    private String getNthPlayerId(int randomInt) {
        return (String) players.toArray()[randomInt];
    }

    public void addUserAndNotify(String playerId) {
        players.add(playerId);
        feedNewMessage(new SimpleMessage(playerId, JOIN));
        shouldInitGame();
    }

    public void removeUserAndNotify(String playerId) {
        players.remove(playerId);
        feedNewMessage(new SimpleMessage(playerId, LEFT));
    }

    private void feedNewMessage(SimpleMessage message) {
        template.convertAndSend("/feed/info", message);
    }
}
