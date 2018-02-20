package pl.tmaj;

import org.springframework.context.ApplicationEvent;

public class NotifyAllEvent extends ApplicationEvent {

    private String message;

    public NotifyAllEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
