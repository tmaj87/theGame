package pl.tmaj;

import java.net.Socket;
import java.util.concurrent.Callable;

import static java.util.UUID.randomUUID;

public class PlayerHandler implements Callable<Socket> {

    private final Socket socket;
    private final String id = randomUUID().toString();

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
