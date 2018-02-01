package pl.tmaj;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class TheGame {

    private ExecutorService threadPool;
    private GameServer gameServer;

    public static void main(String[] args) {
        new TheGame();
    }

    public TheGame() {
        threadPool = newFixedThreadPool(1);
        gameServer = new GameServer();
        threadPool.submit(gameServer);
    }

    public void stop() throws IOException {
        gameServer.stop();
    }
}
