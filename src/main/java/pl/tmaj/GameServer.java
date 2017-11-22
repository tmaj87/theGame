package pl.tmaj;

import pl.tmaj.common.Log4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newCachedThreadPool;

public class GameServer {

    private final Log4j log4j = new Log4j(this);
    private static final ExecutorService pool = newCachedThreadPool();
    private static final boolean INFINITE = true;
    private static final int PORT = 9191;

    private CountDownLatch gate = new CountDownLatch(16);

    public GameServer() {
        listenOn(PORT);
    }

    private void listenOn(int port) {
        try (ServerSocket listener = new ServerSocket(port)) {
            while (INFINITE) {
                final Socket client = listener.accept();
                pool.execute(() -> new PlayerHandler(client, gate));
            }
        } catch (IOException e) {
            log4j.WARN(e.getMessage());
        }
    }
}
