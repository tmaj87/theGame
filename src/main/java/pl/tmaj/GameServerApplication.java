package pl.tmaj;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class GameServerApplication implements CommandLineRunner {

    private final Scoreboard scoreboard;

    public GameServerApplication(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    @Override
    public void run(String... strings) throws Exception {
        new Thread(() ->
                new GameServer(scoreboard)
        ).start();
    }
}