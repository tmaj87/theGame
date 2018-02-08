package pl.tmaj;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.net.Socket;
import java.util.concurrent.Callable;

@Entity
public class PlayerHandler implements Callable<Socket> {

    private final Socket socket;

    @Id
    @GeneratedValue
    private String id;

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
