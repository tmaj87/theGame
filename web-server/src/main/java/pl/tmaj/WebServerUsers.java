package pl.tmaj;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import pl.tmaj.common.SimpleMessage;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static pl.tmaj.common.SimpleMessageType.JOIN;
import static pl.tmaj.common.SimpleMessageType.LEFT;

@Component
public class WebServerUsers {

    private List<String> users = new CopyOnWriteArrayList<>();
    private Map<String, String> names = new ConcurrentHashMap<>();
    private SimpMessagingTemplate template;

    public WebServerUsers(SimpMessagingTemplate template) {
        this.template = template;
    }

    public String pickRandomUser() {
        Random random = new Random();
        int randomInt = random.nextInt(users.size());
        String user = users.get(randomInt);
        return getUserNameOr(user);
    }

    public int getUsersCount() {
        return users.size();
    }

    public void addUserAndNotifyAll(String user) {
        users.add(user);
        notifyAll(new SimpleMessage(user, JOIN));
    }

    public void removeUserAndNotifyAll(String user) {
        users.remove(user);
        notifyAll(new SimpleMessage(user, LEFT));
    }

    public String getUserNameOr(String user) {
        return names.getOrDefault(user, user);
    }

    public void setUserName(String user, String name) {
        names.put(user, name);
    }

    public void clearAllUsers() {
        users.clear();
    }

    public void notifyAll(SimpleMessage message) {
        template.convertAndSend("/feed/info", message);
    }
}
