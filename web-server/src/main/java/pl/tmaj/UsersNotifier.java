package pl.tmaj;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import static pl.tmaj.SimpleMessageType.*;

@Component
public class UsersNotifier {

    private static final String DESTINATION = "/feed/info";

    private SimpMessagingTemplate template;

    public UsersNotifier(SimpMessagingTemplate template) {
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
        template.convertAndSend(DESTINATION, message);
    }
}
