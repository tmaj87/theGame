package pl.tmaj;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import pl.tmaj.common.SimpleMessage;

import static pl.tmaj.common.SimpleMessageType.MESSAGE;

@Controller
public class WebSocketController {

    @MessageMapping("/message")
    @SendTo("/feed/info")
    public SimpleMessage info(SimpleMessage message, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        return new SimpleMessage(message.getContent(), headerAccessor.getSessionId(), MESSAGE);
    }
}
