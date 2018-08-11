package pl.tmaj;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import pl.tmaj.common.SimpleMessage;

import static pl.tmaj.common.SimpleMessageType.*;

@Component
public class UserNotifier {

    private SimpMessagingTemplate template;

    public UserNotifier(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void notifyJoin(String user) {
        notifyAll(new SimpleMessage(user, JOIN));
    }

    public void notifyLeft(String user) {
        notifyAll(new SimpleMessage(user, LEFT));
    }

    public void notifyCount(int count) {
        notifyAll(new SimpleMessage(String.valueOf(count), COUNT));
    }

    public void notifyWon(String user) {
        notifyAll(new SimpleMessage(user, WON));
    }

    private void notifyAll(SimpleMessage message) {
        template.convertAndSend("/feed/info", message);
    }
}
