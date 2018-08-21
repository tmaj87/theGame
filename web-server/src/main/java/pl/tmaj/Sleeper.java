package pl.tmaj;

import org.springframework.stereotype.Component;

@Component
public class Sleeper {

    public void sleep(int milliseconds) throws InterruptedException {
        Thread.sleep(milliseconds);
    }
}
