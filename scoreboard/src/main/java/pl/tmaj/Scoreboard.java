package pl.tmaj;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class Scoreboard {

    private final WinnersRepository winnersRepository;

    public Scoreboard(WinnersRepository winnersRepository) {
        this.winnersRepository = winnersRepository;
    }

    @GetMapping("/")
    @ResponseBody
    public Collection<Winner> index() {
        return this.winnersRepository.findAll();
    }
}
