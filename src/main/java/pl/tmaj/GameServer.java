package pl.tmaj;

import pl.tmaj.common.Log4t;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newCachedThreadPool;

public class GameServer {

    private final Log4t log4T = new Log4t(this.getClass().getSimpleName());

    private static final int DEFAULT_PORT = 9191;
    private static final ExecutorService threadPool = newCachedThreadPool();
    private static boolean IS_LISTENING = true;
    private static final CountDownLatch gate = new CountDownLatch(15);

    private GameServer(int port) {
        threadPool.submit(this::gateKeeper);
        listenOn(port);
    }

    public GameServer() {
        this(DEFAULT_PORT);
    }

    private void listenOn(int port) {
        try (ServerSocket listener = new ServerSocket(port)) {
            while (IS_LISTENING) {
                final Socket player = listener.accept();
                newPlayer(player);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void newPlayer(Socket player) {
//        threadPool.submit(() -> new PlayerHandler(player, gate));
        gate.countDown();
    }

    private void gateKeeper() {
        try {
            gate.await();
            exit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exit() {
        IS_LISTENING = false;
    }
}
