package pl.tmaj;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@Component
public class ScoreboardApplication implements CommandLineRunner {

    private final WinnersRepository winnersRepository;

    public ScoreboardApplication(WinnersRepository winnersRepository) {
        this.winnersRepository = winnersRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        Stream.of("John", "Alice", "Mark")
                .forEach(string -> winnersRepository.save(new Winner(string)));
    }
}
