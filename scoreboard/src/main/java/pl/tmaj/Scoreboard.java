package pl.tmaj;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import static java.util.Comparator.reverseOrder;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.*;

@RestController
public class Scoreboard {

    private static final Winner NO_WINNER = new Winner("NO_WINNER");

    private final WinnerRepository winnerRepository;

    public Scoreboard(WinnerRepository winnerRepository) {
        this.winnerRepository = winnerRepository;
    }

    @HystrixCommand(fallbackMethod = "noWinners")
    @GetMapping("/latest")
    public String getBestPlayer() {
        return getLatestWinner().getName();
    }

    private Winner getLatestWinner() {
        Optional<Winner> winner = winnerRepository.getLatest()
                .getContent()
                .stream()
                .findFirst();
        return winner.orElse(NO_WINNER);
    }

    public String noWinners() {
        return NO_WINNER.getName();
    }

    @HystrixCommand(fallbackMethod = "noRepositoryResponse")
    @GetMapping("/best")
    public Map<String, Long> getSortedPlayers() {
        Map<String, Long> groupedPlayers = winnerRepository.findAll()
                .getContent()
                .stream()
                .collect(groupingBy(Winner::getName, counting()));
        return sortPlayersByPointsDesc(groupedPlayers);
    }

    private Map<String, Long> sortPlayersByPointsDesc(Map<String, Long> allPlayersGrouped) {
        return allPlayersGrouped
                .entrySet()
                .stream()
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

    public Map<String, Long> noRepositoryResponse() {
        return new HashMap<>();
    }
}
