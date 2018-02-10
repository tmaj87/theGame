package pl.tmaj;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

import java.util.stream.Stream;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
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
