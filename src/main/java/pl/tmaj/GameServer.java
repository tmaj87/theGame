package pl.tmaj;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static java.util.UUID.randomUUID;
import static java.util.concurrent.Executors.newCachedThreadPool;

public class GameServer implements Runnable {

    private static final int DEFAULT_PORT = 9191; // config server
    private static final int N_PLAYERS = 16; // config server

    private ExecutorService threadPool = newCachedThreadPool();

    @Override
    public void run() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(DEFAULT_PORT);
            List<Callable<Socket>> listeners = listenOn(serverSocket);
            waitForAllPlayers(threadPool.invokeAll(listeners));
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    private List<Callable<Socket>> listenOn(ServerSocket serverSocket) {
        List<Callable<Socket>> listeners = new ArrayList<>();
        for (int i = 0; i < N_PLAYERS; i++) {
            listeners.add(new PlayerHandler(serverSocket));
        }
        return listeners;
    }

    private void waitForAllPlayers(List<Future<Socket>> futures) throws Exception {
        for (Future<Socket> future : futures) {
            Socket socket = future.get();
            PrintWriter toSocket = new PrintWriter(socket.getOutputStream(), true);
            toSocket.write(randomUUID().toString());
            socket.close();
        }
    }
}
