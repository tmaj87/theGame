package pl.tmaj;

import org.slf4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.slf4j.LoggerFactory.getLogger;
import static pl.tmaj.ConfigServer.DEFAULT_PORT;
import static pl.tmaj.ConfigServer.N_PLAYERS;

public class GameServer {

    private Logger log = getLogger(this.getClass());
    private ExecutorService threadPool = Executors.newFixedThreadPool(N_PLAYERS);
    private ServerSocket serverSocket;
    private Scoreboard scoreboard;

    public GameServer(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
        log.info("Program 'Praca Dyplomow' uruchomiony");
        init();
    }

    private void init() {
        try {
            serverSocket = new ServerSocket(DEFAULT_PORT);
            waitForAllPlayers();
            pickWinner();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            stop();
        }
    }

    private void waitForAllPlayers() throws IOException {
        for (int i = 0; i < N_PLAYERS; i++) {
            Socket player = serverSocket.accept();
            threadPool.submit(new PlayerHandler(player));
        }
    }

    private synchronized void pickWinner() {
        Integer randomPlayer = new Random().nextInt(N_PLAYERS);
        scoreboard.save(new Winner(randomPlayer.toString()));
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
