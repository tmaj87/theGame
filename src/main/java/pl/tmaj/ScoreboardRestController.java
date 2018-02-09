package pl.tmaj;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
class ScoreboardRestController {

    private final Scoreboard scoreboard;

    ScoreboardRestController(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    @GetMapping("/")
    String index() {
        return "Witam na serwerze gry.";
    }

    @GetMapping("/winners")
    Collection<Winner> winnerCollection() {
        return this.scoreboard.findAll();
    }
}
