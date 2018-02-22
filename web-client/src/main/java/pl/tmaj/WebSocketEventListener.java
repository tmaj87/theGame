package pl.tmaj;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
public class WebSocketEventListener {

    private WebServer server;

    public WebSocketEventListener(WebServer server) {
        this.server = server;
    }

    @EventListener
    public void clientConnected(SessionConnectEvent event) {
        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
        String playerId = headers.getSessionId();
        server.addUser(playerId);
    }

    @EventListener
    public void clientDisconnect(SessionDisconnectEvent event) {
        String playerId = event.getSessionId();
        server.removeUser(playerId);
    }

    @EventListener
    public void newSubscriber(SessionSubscribeEvent event) {
        server.haveLastPlayerJoined();
    }
}
