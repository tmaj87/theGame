package pl.tmaj;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import static java.util.Collections.reverseOrder;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.*;

@RestController
public class Scoreboard {

    public String noWinner() {
        return "Be the first to win!";
    }

    @HystrixCommand(fallbackMethod = "noWinner")
    @GetMapping("/latest")
    public String getTheBast() {
        Optional<Winner> name = winnerRepository.getLatest().getContent().stream().findFirst();
        return name.isPresent() ? name.get().toString() : "Winner";
    }

    private WinnerRepository winnerRepository;

    public Scoreboard(WinnerRepository winnerRepository) {
        this.winnerRepository = winnerRepository;
    }

    public Map<String, Long> noRepositoryResponse() {
        return new HashMap<>();
    }

    @HystrixCommand(fallbackMethod = "noRepositoryResponse")
    @GetMapping("/best")
    public Map<String, Long> getSortedBestPlayers() {
        Map<String, Long> allPlayersGrouped = winnerRepository.findAll()
                .getContent().stream()
                .collect(groupingBy(Winner::getName, counting()));
        return sortPlayersByBest(allPlayersGrouped);
    }

    private Map<String, Long> sortPlayersByBest(Map<String, Long> allPlayersGrouped) {
        return allPlayersGrouped.entrySet().stream()
                .sorted(comparingByValue(reverseOrder()))
                .collect(toMap(
                        Entry::getKey,
                        Entry::getValue,
                        (v1, v2) -> {
                            throw new IllegalStateException();
                        },
                        LinkedHashMap::new
                ));
    }
}
