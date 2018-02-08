package pl.tmaj;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newCachedThreadPool;

@Component
public class GameServer implements Runnable {

    private static final int DEFAULT_PORT = 9191; // config server
    private static final int N_PLAYERS = 16; // config server

    private ServerSocket serverSocket;
    private ExecutorService threadPool = newCachedThreadPool();

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(DEFAULT_PORT);
            for (int i = 0; i < N_PLAYERS; i++) {
                Socket player = serverSocket.accept();
                threadPool.submit(new PlayerHandler(player));
            }
            stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // finally
    }

    public void stop() throws IOException {
        serverSocket.close();
        threadPool.shutdownNow();
    }
}

@RepositoryRestResource
interface Scoreboard extends JpaRepository<Winner, Long> {}

@Entity
class Winner {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    protected Winner() {}

    public Winner(String name) {
        this.name = name;
    }
}