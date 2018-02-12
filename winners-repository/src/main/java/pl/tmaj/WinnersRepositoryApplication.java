package pl.tmaj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@SpringBootApplication
public class WinnersRepositoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(WinnersRepositoryApplication.class, args);
    }
}

@RestController
class WinnerRestController {

    private WinnersRepository winnersRepository;

    public WinnerRestController(WinnersRepository winnersRepository) {
        this.winnersRepository = winnersRepository;
    }

    @RequestMapping("/")
    public List<String> getAllWinners() {
        return winnersRepository.findAll()
                .stream()
                .map(Winner::toString)
                .collect(toList());
    }
}
