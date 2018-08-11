package pl.tmaj;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UsersHandler {

    private static final User EMPTY_USER = new User("", "");
    private Map<String, User> users = new ConcurrentHashMap<>();

    public boolean addUser(String user) {
        return users.put(user, new User(user, user)) instanceof User;
    }

    public String getUser(String user) {
        return users.get(user).getNick();
    }

    public boolean removeUser(String user) {
        return users.remove(user) != null;
    }

    public boolean setUserName(String user, String nick) {
        return users.replace(user, new User(user, nick)) instanceof User;
    }

    public User pickRandomUser() {
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
        return EMPTY_USER;
    }

    public int count() {
        return users.size();
    }

    public void clear() {
        users.clear();
    }
}
