package pl.tmaj;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.slf4j.LoggerFactory.getLogger;

@Component
public class GameServer {

    @Value("${max.players:1}")
    private int maxPlayers;
    @Value("${game.port:1}")
    private int gamePort;

    private Logger log = getLogger(this.getClass());
    private ExecutorService threadPool = newFixedThreadPool(maxPlayers);
    private ServerSocket serverSocket;

    protected GameServer() {
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
