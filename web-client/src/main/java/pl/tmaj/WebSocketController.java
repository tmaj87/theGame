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
    }

    public void info(String message) throws Exception {
        template.convertAndSend("/feed/info", message);
    }

    public void toPlayer(String message, String playerId) {
        template.convertAndSend("/feed/{playerId}", message);
    }
}
