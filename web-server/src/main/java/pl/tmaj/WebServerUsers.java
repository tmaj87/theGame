package pl.tmaj;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import pl.tmaj.common.SimpleMessage;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import static pl.tmaj.common.SimpleMessageType.JOIN;
import static pl.tmaj.common.SimpleMessageType.LEFT;

@Component
public class WebServerUsers {

    private static final User NO_WINNERS = new User("", "");
    private Map<String, User> users = new ConcurrentHashMap<>();
    private SimpMessagingTemplate template;

    public WebServerUsers(SimpMessagingTemplate template) {
        this.template = template;
    }

    public boolean addUser(String user) {
        return users.put(user, new User(user, user)) instanceof User;
    }

    public boolean setUserName(String user, String nick) {
        return users.replace(user, new User(user, nick)) instanceof User;
    }

    public String getUser(String user) {
        return users.get(user).getNick();
    }

    public int getUsersCount() {
        return users.size();
    }

    public void addUserAndNotifyAll(String user) {
        addUser(user);
        notifyAll(new SimpleMessage(user, JOIN));
    }

    public void removeUserAndNotifyAll(String user) {
        users.remove(user);
        notifyAll(new SimpleMessage(user, LEFT));
    }

    public void notifyAll(SimpleMessage message) {
        template.convertAndSend("/feed/info", message);
    }

    public void restartGame() {
        users.clear();
    }

    public String getWinner() {
        User user = pickRandomUser();
        return user.getNick();
    }

    private User pickRandomUser() {
        Random random = new Random();
        int randomInt = random.nextInt(users.size());
        int pointer = 0;
        for (Entry<String, User> entry : users.entrySet()) {
            final User user = entry.getValue();
            if (pointer == randomInt) {
                return user;
            } else {
                pointer++;
            }
        }
        return NO_WINNERS;
    }
}
