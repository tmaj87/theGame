package pl.tmaj;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class GameServerApplication implements CommandLineRunner {

    private final WinnersRepository winnersRepository;

    public GameServerApplication(WinnersRepository winnersRepository) {
        this.winnersRepository = winnersRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        new Thread(() ->
                new GameServer(winnersRepository)
        ).start();
    }
}