package pl.tmaj;

import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.Callable;

import static java.util.UUID.randomUUID;

public class PlayerHandler implements Callable<Socket> {

    private final String id = randomUUID().toString();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerHandler that = (PlayerHandler) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(socket, that.socket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, socket);
    }
}
