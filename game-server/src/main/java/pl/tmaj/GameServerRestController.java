package pl.tmaj;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.tmaj.common.RandomString;
import pl.tmaj.common.SHA256;

@RestController
@RefreshScope // curl http://localhost:8080/refresh -d{}
public class GameServerRestController {

    private int maxPlayers;
    private int gamePort;

    public GameServerRestController(@Value("${max.players}") int maxPlayers, @Value("${game.port}") int gamePort) {
        this.maxPlayers = maxPlayers;
        this.gamePort = gamePort;
    }

    @RequestMapping("/")
    public String index() {
        String playerName = RandomString.ofLength(12, false);
        return "Welcome at GameServer, your name is " + playerName + " and playerId: " + SHA256.of(playerName)
                + ", you are of " + maxPlayers + " players in game started on " + gamePort + " port";
    }
}
