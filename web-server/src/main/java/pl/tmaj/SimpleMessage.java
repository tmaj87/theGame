package pl.tmaj;

import java.util.Objects;

public class SimpleMessage {

    private String content;
    private SimpleMessageType type;
    private String user;

    public SimpleMessage() {
    }

    public SimpleMessage(String content, SimpleMessageType type) {
        this.content = content;
        this.type = type;
    }

    public SimpleMessage(String content, String user, SimpleMessageType type) {
        this.content = content;
        this.user = user;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String name) {
        this.content = name;
    }

    public SimpleMessageType getType() {
        return type;
    }

    public void setType(SimpleMessageType type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "SimpleMessage{" +
                "content='" + content + '\'' +
                ", type=" + type +
                ", user='" + user + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleMessage that = (SimpleMessage) o;
        return Objects.equals(content, that.content) &&
                type == that.type &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, type, user);
    }
}
