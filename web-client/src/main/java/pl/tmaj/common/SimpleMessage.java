package pl.tmaj.common;

public class SimpleMessage {

    private String content;
    private SimpleMessageType type;

    public SimpleMessage() {
    }

    public SimpleMessage(String content, SimpleMessageType type) {
        this.content = content;
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
}
