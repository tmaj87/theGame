package pl.tmaj;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import pl.tmaj.common.HashIt;
import pl.tmaj.common.RandomString;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

// hint: https://github.com/salmar/spring-websocket-chat
@Controller
public class WebSocketController {

    private SimpMessagingTemplate template;

    public WebSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/message")
    public void setName(SimpleMessage message) throws Exception {
        // associate name with id, in WinnerRepository
    }

    public void info() throws Exception {
        template.convertAndSend("/feed/info", "pong"); // "/feed/{userId}"
    }
}

@Component
class WebSocketEventListener {

    private Queue<SessionConnectEvent> sessions = new ConcurrentLinkedQueue<>();
    private Map<String, MessageHeaders> players = new ConcurrentHashMap<>();
    private int maxPlayers;


    public WebSocketEventListener(@Value("${max.players:1}") int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    @EventListener
    public void handleNewConnection(SessionConnectEvent event) {
        MessageHeaders headers = event.getMessage().getHeaders();
        String randomEvents = RandomString.ofLength(maxPlayers) + event.getTimestamp();
        String playerId = HashIt.of(randomEvents);
        players.put(playerId, headers);
        sessions.add(event);
        System.out.println(sessions.size()); // break me!
    }
}
