package pl.tmaj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

@SpringBootApplication
public class GameServer {

    private static final Logger log = LoggerFactory.getLogger(GameServer.class);

    private static final int DEFAULT_PORT = 9191; // config server
    private static final int N_PLAYERS = 16; // config server

    private ServerSocket serverSocket;
    private ExecutorService threadPool = newFixedThreadPool(N_PLAYERS);
    private Scoreboard scoreboard;

    public static void main(String[] args) {
        SpringApplication.run(GameServer.class, args);
    }

    @Bean
    public CommandLineRunner startServer(Scoreboard scoreboard) {
        return (args) -> {
            try {
                log.info("Program \\Praca \\Dyplomowa uruchomiony.");
                serverSocket = new ServerSocket(DEFAULT_PORT);
                for (int i = 0; i < N_PLAYERS; i++) {
                    Socket player = serverSocket.accept();
                    threadPool.submit(new PlayerHandler(player));
                }
                pickWinner(scoreboard);
                stop();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            // finally
        };
    }

    private synchronized void pickWinner(Scoreboard scoreboard) {
        String randomPlayer = String.valueOf(new Random().nextInt(N_PLAYERS));
        scoreboard.save(new Winner(randomPlayer));
    }

    private void stop() throws IOException {
        serverSocket.close();
        threadPool.shutdownNow();
    }
}
