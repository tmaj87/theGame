package pl.tmaj;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class ScoreboardRestController {

    private final Scoreboard scoreboard;

    public ScoreboardRestController(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    @GetMapping("/")
    public Collection<Winner> index() {
        return this.scoreboard.findAll();
    }
}
