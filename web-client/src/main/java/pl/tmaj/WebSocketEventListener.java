package pl.tmaj;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

    private WebServer server;

    public WebSocketEventListener(WebServer server) {
        this.server = server;
    }

    @EventListener
    public void handleNewConnection(SessionConnectEvent event) {
        String playerId = getSimpSessionId(event);
        server.addUser(playerId);
    }


    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        String playerId = event.getSessionId();
        server.removeUser(playerId);
    }

    private String getSimpSessionId(SessionConnectEvent event) {
        return (String) event.getMessage().getHeaders().get("simpSessionId");
    }
}
