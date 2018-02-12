package pl.tmaj;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@SpringBootApplication
public class WinnersRepositoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(WinnersRepositoryApplication.class, args);
    }
}

@Component
class DemoData implements CommandLineRunner {
    private final WinnersRepository winnersRepository;

    public DemoData(WinnersRepository winnersRepository) {
        this.winnersRepository = winnersRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        Stream.of("Josh", "Mark", "Elen", "Joe")
                .forEach(name -> winnersRepository
                        .save(new Winner(name))
                );
    }
}
