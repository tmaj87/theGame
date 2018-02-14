package pl.tmaj;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Configuration
@EnableWebSocket
public class WebClient implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(register(), "/register");
    }

    @Bean
    public WebSocketHandler register() {
        return new ConnectionHandler();
    }
}

class ConnectionHandler extends TextWebSocketHandler {

    static private Queue<WebSocketSession> sessions = new ConcurrentLinkedQueue<>();
    private int maxPlayers = 3;

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // read message ??
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws IOException {
        sessions.add(webSocketSession);
        webSocketSession.sendMessage(new TextMessage("pong"));

        if (sessions.size() >= maxPlayers) {
            for (WebSocketSession session : sessions) {
                session.sendMessage(new TextMessage("bye"));
                session.close();
            }
        }
    }
}
