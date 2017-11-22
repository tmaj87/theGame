package pl.tmaj;

import pl.tmaj.common.Log4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newCachedThreadPool;

public class TheGame {

    private static final ExecutorService pool = newCachedThreadPool();
    private final Log4j log4j = new Log4j(this);

    private static final int PORT = 9191;

    private TheGame() {
        listenOn(PORT);
    }

    private void listenOn(int port) {
        try (ServerSocket listener = new ServerSocket(port)) {
            while (true) {
                final Socket client = listener.accept();
                pool.execute(() -> new PlayerHandler(client));
            }
        } catch (IOException e) {
            log4j.WARN(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new TheGame();
    }

}
