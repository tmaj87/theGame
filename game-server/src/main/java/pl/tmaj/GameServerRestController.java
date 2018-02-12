package pl.tmaj;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import pl.tmaj.common.RandomString;
import pl.tmaj.common.SHA256;

@RestController
public class GameServerRestController {

    @RequestMapping("/")
    @ResponseBody
    public String index() {
        String playerName = RandomString.ofLength(12, false);
        return "Welcome at GameServer, your name is " + playerName + " and playerId: " + SHA256.of(playerName);
    }
}
