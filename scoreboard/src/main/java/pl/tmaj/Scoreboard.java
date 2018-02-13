package pl.tmaj;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;

@RestController
public class Scoreboard {

    private WinnerRepository winnerRepository;

    public Scoreboard(WinnerRepository winnerRepository) {
        this.winnerRepository = winnerRepository;
    }

    public List<String> fallback() {
        return singletonList("Hello traveler");
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping("/")
    public List<String> index() {
        return winnerRepository.getAll()
                .getContent()
                .stream()
                .map(Winner::getName)
                .collect(Collectors.toList());
    }
}
