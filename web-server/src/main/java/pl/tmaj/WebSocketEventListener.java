package pl.tmaj;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import static org.springframework.messaging.simp.stomp.StompHeaderAccessor.wrap;

@Component
public class WebSocketEventListener {

    private WebServer server;
    private UsersHandler users;
    private UsersNotifier notifier;

    public WebSocketEventListener(WebServer server, UsersHandler users, UsersNotifier notifier) {
        this.server = server;
        this.users = users;
        this.notifier = notifier;
    }

    @EventListener
    public void clientConnected(SessionConnectEvent event) {
        String playerId = getStopmSessionId(event);
        users.addUser(playerId);
        notifier.notifyJoin(playerId);
    }

    private String getStopmSessionId(SessionConnectEvent event) {
        StompHeaderAccessor headers = wrap(event.getMessage());
        return headers.getSessionId();
    }

    @EventListener
    public void clientDisconnect(SessionDisconnectEvent event) {
        String playerId = event.getSessionId();
        users.removeUser(playerId);
        notifier.notifyLeft(playerId);
    }

    @EventListener
    public void newSubscriber(SessionSubscribeEvent event) {
        server.ping();
    }
}
