package pl.tmaj;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class WebServer {

    private final WinnerRepository repository;
    private final UsersHandler users;
    private final UsersNotifier notifier;

    @Value("${max.players:1}")
    private int maxPlayers;

    public WebServer(WinnerRepository repository,
                     UsersHandler users,
                     UsersNotifier notifier) {
        this.repository = repository;
        this.users = users;
        this.notifier = notifier;
    }

    public void ping() {
        if (users.count() >= maxPlayers) {
            pickWinnerAndAnnounce();
            restartGame();
        }
    }

    private void pickWinnerAndAnnounce() {
        Winner winner = pickWinner();
        saveAndNotify(winner);
    }

    private Winner pickWinner() {
        return new Winner(users.pickRandomUser().getNick());
    }

    private void saveAndNotify(Winner winner) {
        repository.save(winner);
        notifier.notifyWon(winner.getName());
    }

    private void restartGame() {
        users.clear();
    }

    public int getMissingPlayers() {
        return maxPlayers - users.count();
    }
}
