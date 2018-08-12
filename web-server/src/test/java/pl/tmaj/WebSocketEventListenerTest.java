package pl.tmaj;

import org.junit.jupiter.api.Test;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import static org.mockito.Mockito.*;

public class WebSocketEventListenerTest {

    private static final String DUMMY_USER = "dummyUser";

    private final WebServer server = mock(WebServer.class);
    private final UsersHandler users = mock(UsersHandler.class);
    private final UsersNotifier notifier = mock(UsersNotifier.class);
    private final SessionConnectEvent connectEvent = mock(SessionConnectEvent.class);
    private final SessionDisconnectEvent disconnectEvent = mock(SessionDisconnectEvent.class);

    private WebSocketEventListener listener = new WebSocketEventListener(server, users, notifier);

    @Test
    void shouldHandleClientConnection() {
        listener.clientConnected(connectEvent);

        verify(users).addUser(any());
        verify(notifier).notifyJoin(any());
    }

    @Test
    void shouldHandleClientDisconnection() {
        when(disconnectEvent.getSessionId()).thenReturn(DUMMY_USER);

        listener.clientDisconnect(disconnectEvent);

        verify(users).removeUser(DUMMY_USER);
        verify(notifier).notifyLeft(DUMMY_USER);
    }

    @Test
    void shouldHandleNewSubscriber() {
        listener.newSubscriber(null);

        verify(server).ping();
    }
}
