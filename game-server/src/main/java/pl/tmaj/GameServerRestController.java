package pl.tmaj;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameServerRestController {

    private WinnerRepository winnerRepository;

    public GameServerRestController(WinnerRepository winnerRepository) {
        this.winnerRepository = winnerRepository;
    }

    @RequestMapping("/{name}")
    public String index(@PathVariable String name) {
        Winner winner = new Winner(name);
        winnerRepository.save(winner);
        return winner.getName();
    }
}
