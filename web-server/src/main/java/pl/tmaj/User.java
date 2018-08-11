package pl.tmaj;

import java.util.Objects;

public class User {

    private final String sessionId;
    private final String nick;

    public User(String sessionId, String nick) {
        this.sessionId = sessionId;
        this.nick = nick;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getNick() {
        return nick;
    }

    @Override
    public String toString() {
        return "User{" +
                "sessionId='" + sessionId + '\'' +
                ", nick='" + nick + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(sessionId, user.sessionId) &&
                Objects.equals(nick, user.nick);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, nick);
    }
}
