package pl.tmaj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

@SpringBootApplication
public class TheGame {

    private ExecutorService threadPool;
    private GameServer gameServer;

    public static void main(String[] args) {
        SpringApplication.run(TheGame.class, args);
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
