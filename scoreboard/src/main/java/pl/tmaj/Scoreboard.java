package pl.tmaj;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

@RestController
public class Scoreboard {

    private WinnerRepository winnerRepository;

    public Scoreboard(WinnerRepository winnerRepository) {
        this.winnerRepository = winnerRepository;
    }

    public List<String> fallback() {
        return Stream.of("AAA", "DDD", "XXX")
                .collect(toList());
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping("/list")
    public List<String> index() {
        return winnerRepository.getAll()
                .getContent()
                .stream()
                .map(Winner::getName)
                .collect(toList());
    }
}
