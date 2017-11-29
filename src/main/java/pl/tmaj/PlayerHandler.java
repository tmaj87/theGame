package pl.tmaj;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;

class PlayerHandler implements Callable<Socket> {

    private final ServerSocket serverSocket;

    public PlayerHandler(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public Socket call() throws Exception {
        return serverSocket.accept();
    }
}
