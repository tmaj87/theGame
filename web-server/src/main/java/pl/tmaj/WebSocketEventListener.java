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
    private UsersHandler users;
    private UsersNotifier notifier;

    public WebSocketEventListener(WebServer server, UsersHandler users, UsersNotifier notifier) {
        this.server = server;
        this.users = users;
        this.notifier = notifier;
    }

    @EventListener
    public void clientConnected(SessionConnectEvent event) {
        StompHeaderAccessor headers = StompHeaderAccessor.wrap(event.getMessage());
        String playerId = headers.getSessionId();
        users.addUser(playerId);
        notifier.notifyJoin(playerId);
    }

    @EventListener
    public void clientDisconnect(SessionDisconnectEvent event) {
        String playerId = event.getSessionId();
        users.removeUser(playerId);
        notifier.notifyLeft(playerId);
    }

    @EventListener
    public void newSubscriber(SessionSubscribeEvent event) {
        server.checkPlayerCount();
    }
}
