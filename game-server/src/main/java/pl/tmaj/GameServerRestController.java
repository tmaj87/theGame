package pl.tmaj;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.tmaj.common.RandomString;

@RestController
@RefreshScope
public class GameServerRestController {

    private int maxPlayers;
    private int gamePort;
    private WinnerRepository winnerRepository;

    public GameServerRestController(
            @Value("${max.players}") int maxPlayers,
            @Value("${game.port}") int gamePort,
            WinnerRepository winnerRepository) {
        this.maxPlayers = maxPlayers;
        this.gamePort = gamePort;
        this.winnerRepository = winnerRepository;
    }

    @RequestMapping("/")
    public String index() {
        String name = RandomString.ofLength(12, false);
        winnerRepository.save(new Winner(name));
        return "Welcome at GameServer, your name is " + name
                + ", you are of " + maxPlayers + " players in game started on " + gamePort + " port";
    }
}
