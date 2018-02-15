package pl.tmaj;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private SimpMessagingTemplate template;

    public WebSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/message")
    public void setName(SimpleMessage message) throws Exception {
        String name = message.getContent();
        // associate name with id, in WinnerRepository
    }

    public void info() throws Exception {
        template.convertAndSend("/feed/info", "pong"); // "/feed/{userId}"
    }
}
