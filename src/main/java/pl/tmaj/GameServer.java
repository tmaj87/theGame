package pl.tmaj;

import pl.tmaj.common.Log4t;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newCachedThreadPool;
import static pl.tmaj.common.Log4t.getInstanceFor;

public class GameServer implements Runnable {

    public static final int DEFAULT_PORT = 9191;

    private final Log4t log4T = getInstanceFor(this);
    private final ExecutorService threadPool = newCachedThreadPool();
    private boolean IS_LISTENING = true;
    private final CountDownLatch gameTrigger = new CountDownLatch(15);

    private void listen() {
        try (ServerSocket listener = new ServerSocket(DEFAULT_PORT)) {
            while (IS_LISTENING) {
                final Socket player = listener.accept();
                newPlayer(player);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void newPlayer(Socket player) {
        gameTrigger.countDown();
    }

    private void awaitPlayers() {
        try {
            gameTrigger.await();
            exit();
        } catch (Exception e) {
            log4T.WARN(e.getMessage());
        }
    }

    public void exit() {
        IS_LISTENING = false;
        threadPool.shutdown();
    }

    @Override
    public void run() {
        threadPool.submit(this::awaitPlayers);
        listen();
    }
}
