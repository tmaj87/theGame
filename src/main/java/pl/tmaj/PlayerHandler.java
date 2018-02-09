package pl.tmaj;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.net.Socket;
import java.util.concurrent.Callable;

public class PlayerHandler extends Logged implements Callable<Socket> {

    @Id
    @GeneratedValue
    private String id;
    private final Socket socket;

    public PlayerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public Socket call() throws Exception {
        return socket;
    }

    public String getId() {
        return id;
    }
}
