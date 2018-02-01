package pl.tmaj;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Callable;

class PlayerHandler implements Callable<Socket> {

    private final Socket socket;
    private final String id = "PlayerX";

    PlayerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public Socket call() throws IOException {
        sendId();
        return socket;
    }

    private void sendId() throws IOException {
        PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true);
        toSocket.write("PlayerX");
    }
}
