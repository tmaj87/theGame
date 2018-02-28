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

    private Map<String, String> names = new ConcurrentHashMap<>();

    @MessageMapping("/message")
    @SendTo("/feed/info")
    public SimpleMessage info(SimpleMessage message, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        String sessionId = headerAccessor.getSessionId();
        return new SimpleMessage(message.getContent(), names.getOrDefault(sessionId, sessionId), MESSAGE);
    }

    @MessageMapping("/username")
    public void username(String name, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        names.put(headerAccessor.getSessionId(), name);
    }
}
