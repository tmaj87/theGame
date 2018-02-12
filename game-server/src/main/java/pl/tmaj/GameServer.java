package pl.tmaj;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.slf4j.LoggerFactory.getLogger;

@RefreshScope
@RestController
public class GameServer {

    @Value("${max.players}")
    private int maxPlayers;
    @Value("${game.port}")
    private int gamePort;

    private Logger log = getLogger(this.getClass());
    private ExecutorService threadPool = newFixedThreadPool(maxPlayers);
    private ServerSocket serverSocket;

    @RequestMapping("/")
    @ResponseBody
    public String index() {
        return "Welcome at GameServer";
    }

    private void init() {
        log.info("Program 'Praca Dyplomow' uruchomiony");
        try {
            serverSocket = new ServerSocket(gamePort);
            waitForAllPlayers();
            pickWinner();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            stop();
        }
    }

    private void waitForAllPlayers() throws IOException {
        for (int i = 0; i < maxPlayers; i++) {
            Socket player = serverSocket.accept();
            threadPool.submit(new PlayerHandler(player));
        }
    }

    private synchronized void pickWinner() {
        // pick random winner
        // get winner id from PlayerHandler object
        // send winner id to WinnersRepository
    }

    private void stop() {
        try {
            serverSocket.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            threadPool.shutdownNow();
        }
    }
}
