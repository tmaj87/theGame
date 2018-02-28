package pl.tmaj;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import pl.tmaj.common.SimpleMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static pl.tmaj.common.SimpleMessageType.MESSAGE;

@Controller
public class WebSocketController {

    private WebServerUsers users;

    public WebSocketController(WebServerUsers users) {
        this.users = users;
    }

    @MessageMapping("/message")
    @SendTo("/feed/info")
    public SimpleMessage info(SimpleMessage message, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        String sessionId = headerAccessor.getSessionId();
        return new SimpleMessage(message.getContent(), users.getUserNameOrDefault(sessionId), MESSAGE);
    }

    @MessageMapping("/username")
    public void username(String name, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        users.setUserName(headerAccessor.getSessionId(), name);
    }
}
