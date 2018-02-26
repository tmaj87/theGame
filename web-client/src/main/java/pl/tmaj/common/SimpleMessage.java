package pl.tmaj.common;

public class SimpleMessage {

    private String content;
    private SimpleMessageType type;
    private String user;

    public SimpleMessage() {
    }

    public SimpleMessage(String content, SimpleMessageType type) {
        this.content = content;
        this.user = "SYSTEM";
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
}
