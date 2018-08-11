package pl.tmaj;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import pl.tmaj.common.SimpleMessage;

import static pl.tmaj.common.SimpleMessageType.MESSAGE;

@Controller
public class WebSocketController {

    private UsersHandler users;

    public WebSocketController(UsersHandler users) {
        this.users = users;
    }

    @MessageMapping("/message")
    @SendTo("/feed/info")
    public SimpleMessage info(SimpleMessage inbound, SimpMessageHeaderAccessor headers) throws Exception {
        String username = getUserBySessionId(headers.getSessionId());
        SimpleMessage outbound = new SimpleMessage(inbound.getContent(), username, MESSAGE);
        return doNotSendEmptyMessage(outbound);
    }

    private String getUserBySessionId(String sessionId) {
        return users.getUser(sessionId);
    }

    @MessageMapping("/username")
    public void username(String name, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        users.setUserName(headerAccessor.getSessionId(), name);
    }

    private SimpleMessage doNotSendEmptyMessage(SimpleMessage outboundMessage) {
        return outboundMessage.getContent().length() > 0 ? outboundMessage : null;
    }
}
