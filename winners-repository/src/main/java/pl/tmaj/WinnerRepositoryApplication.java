package pl.tmaj;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

@SpringBootApplication
@EnableDiscoveryClient
public class WinnerRepositoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(WinnerRepositoryApplication.class, args);
    }
}

@Component
class DemoData implements CommandLineRunner {

    private final WinnerRepository winnerRepository;

    DemoData(WinnerRepository winnerRepository) {
        this.winnerRepository = winnerRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        Stream.of("Josh", "Mark", "Elen", "Joe")
                .forEach(name -> winnerRepository
                        .save(new Winner(name))
                );
    }
}
