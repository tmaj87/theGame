package pl.tmaj;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newCachedThreadPool;

public class GameServer implements Runnable {

    private static final int DEFAULT_PORT = 9191; // config server
    private static final int N_PLAYERS = 16; // config server

    private ServerSocket serverSocket;
    private ExecutorService threadPool = newCachedThreadPool();

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(DEFAULT_PORT);
            for (int i = 0; i < N_PLAYERS; i++) {
                Socket player = serverSocket.accept();
                threadPool.submit(new PlayerHandler(player));
            }
            stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // finally
    }

    public void stop() throws IOException {
        serverSocket.close();
        threadPool.shutdownNow();
    }
}
