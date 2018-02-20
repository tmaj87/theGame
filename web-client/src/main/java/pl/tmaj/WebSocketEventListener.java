package pl.tmaj;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import pl.tmaj.common.SimpleMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketEventListener {

    private static final String DEFAULT_PLAYER_NAME = "";
    private Map<String, String> players = new ConcurrentHashMap<>();
    private int maxPlayers;
    private SimpMessagingTemplate template;


    public WebSocketEventListener(@Value("${max.players:1}") int maxPlayers, SimpMessagingTemplate template) {
        this.maxPlayers = maxPlayers;
        this.template = template;
    }

    @EventListener
    public void handleNewConnection(SessionConnectEvent event) {
        String playerId = getSimpSessionId(event);
        players.put(playerId, DEFAULT_PLAYER_NAME);
        feedInfo(playerId + " joined");
    }

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        String playerId = event.getSessionId();
        players.remove(playerId);
        feedInfo(playerId + " left");
    }

    private String getSimpSessionId(SessionConnectEvent event) {
        return (String) event.getMessage().getHeaders().get("simpSessionId");
    }

    private void feedInfo(String content) {
        template.convertAndSend("/feed/info", new SimpleMessage(content));
    }
}
