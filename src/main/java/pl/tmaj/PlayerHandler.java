package pl.tmaj;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

class PlayerHandler implements Callable<Socket> {

    private static final boolean IS_CONNECTED = true;
    private final Socket socket;
    private final String id = "PlayerX";

    PlayerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public Socket call() throws IOException {
        Executors.newFixedThreadPool(1).submit(() -> {
            PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true);
            while (IS_CONNECTED) {
                Thread.sleep(100);
                toSocket.write(id);
            }
        });
        return socket;
    }

}
