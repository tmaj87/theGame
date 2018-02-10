package pl.tmaj;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class ScoreboardApplication implements CommandLineRunner {

    private final Scoreboard scoreboard;

    public ScoreboardApplication(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    @Override
    public void run(String... strings) throws Exception {
        Stream.of("John", "Alice", "Mark")
                .forEach(string -> scoreboard.save(new Winner(string)));
    }
}
