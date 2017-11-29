package pl.tmaj;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static java.util.concurrent.Executors.newCachedThreadPool;

public class GameServer implements Runnable {

    public static final int DEFAULT_PORT = 9191; // config server
    public static final int N_PLAYERS = 16; // config server

    private ServerSocket serverSocket;
    private final ExecutorService threadPool = newCachedThreadPool();

    private List<Callable<Socket>> spawnThreads() throws Exception {
        serverSocket = new ServerSocket(DEFAULT_PORT);
        List<Callable<Socket>> listeners = new ArrayList<>();
        for (int i = 0; i < N_PLAYERS; i++) {
            listeners.add(new PlayerHandler(serverSocket));
        }
        return listeners;
    }

    @Override
    public void run() {
        try {
            waitForAllPlayers(threadPool.invokeAll(spawnThreads()));
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    private void waitForAllPlayers(List<Future<Socket>> futures) throws Exception {
        for (Future<Socket> future : futures) {
            Socket socket = future.get();
            // ...
            socket.close();
        }
    }
}
