package pl.tmaj;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import pl.tmaj.common.HashIt;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketEventListener {

    private static final String DEFAULT_PLAYER_NAME = "";
    private Map<String, String> players = new ConcurrentHashMap<>();
    private int maxPlayers;


    public WebSocketEventListener(@Value("${max.players:1}") int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    @EventListener
    public void handleNewConnection(SessionConnectEvent event) {
        String playerId = getSimpSessionId(event);
        players.put(HashIt.of(playerId), DEFAULT_PLAYER_NAME);
    }

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        String playerId = event.getSessionId();
        players.remove(HashIt.of(playerId));
    }

    private String getSimpSessionId(SessionConnectEvent event) {
        return (String) event.getMessage().getHeaders().get("simpSessionId");
    }
}
