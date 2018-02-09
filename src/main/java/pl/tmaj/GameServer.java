package pl.tmaj;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static pl.tmaj.ConfigServer.N_PLAYERS;

@SpringBootApplication
public class GameServer extends Logged {

    private ServerSocket serverSocket;
    private ExecutorService threadPool = newFixedThreadPool(N_PLAYERS);

    public static void main(String[] args) {
        SpringApplication.run(GameServer.class, args);
    }

    @Bean
    ApplicationRunner run(Scoreboard scoreboard) {
        log.info("Program \\Praca \\Dyplomowa uruchomiony.");
        return args ->
            Stream.of("misterOne", "secondJoe", "thirdMarry").forEach(name -> scoreboard.save(new Winner(name)));
    }
}
