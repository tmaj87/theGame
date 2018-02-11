package pl.tmaj;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import pl.tmaj.common.Winner;
import pl.tmaj.common.WinnersRepository;

import java.util.stream.Stream;

@SpringBootApplication
//@EnableDiscoveryClient
//@EnableFeignClients
public class ScoreboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScoreboardApplication.class, args);
    }
}

@Component
class SampleDataProvider implements CommandLineRunner {
    private final WinnersRepository winnersRepository;

    public SampleDataProvider(WinnersRepository winnersRepository) {
        this.winnersRepository = winnersRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        Stream.of("John", "Alice", "Mark")
                .forEach(string -> winnersRepository.save(new Winner(string)));
    }
}
