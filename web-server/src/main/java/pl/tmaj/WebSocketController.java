package pl.tmaj;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import pl.tmaj.common.SimpleMessage;

import static pl.tmaj.common.SimpleMessageType.MESSAGE;

@Controller
public class WebSocketController {

    private WebServerUsers users;

    public WebSocketController(WebServerUsers users) {
        this.users = users;
    }

    @MessageMapping("/message")
    @SendTo("/feed/info")
    public SimpleMessage info(SimpleMessage receivedMessage, SimpMessageHeaderAccessor headers) throws Exception {
        String sessionId = headers.getSessionId();
        String username = users.getUserNameOr(sessionId);
        SimpleMessage outboundMessage = new SimpleMessage(receivedMessage.getContent(), username, MESSAGE);
        return doNotSendEmptyMessage(outboundMessage);
    }

    @MessageMapping("/username")
    public void username(String name, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        users.setUserName(headerAccessor.getSessionId(), name);
    }

    private SimpleMessage doNotSendEmptyMessage(SimpleMessage outboundMessage) {
        return outboundMessage.getContent().length() > 0 ? outboundMessage : null;
    }
}
