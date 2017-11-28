package pl.tmaj;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameServerTest {

    private static final String HOST = "localhost";
    private static final int DEFAULT_PORT = 9191;

    private ExecutorService executorService;
    private GameServer gameServer;

    @BeforeEach
    void before() {
        executorService = newFixedThreadPool(1);
        gameServer = new GameServer();
        executorService.submit(gameServer);
    }

    @AfterEach
    void after() {
        gameServer.exit();
        executorService.shutdown();
    }

    @Test
    void shouldListenOnDefaultPort() {
        assertTrue(isGamePortOpen());
    }

    @Test
    void shouldNotListenOnOtherPort() {
        assertFalse(isGamePortOpen(8181));
    }

    @Test
    void shouldStopListeningAfter16ThPlayer() {
        connect16Players();
        assertFalse(isGamePortOpen());
    }

    @Test
    void shouldGenerateUniqueIdForEveryPlayer() {
        assertTrue(null);
    }

    @Test
    void shouldTestSingleCallable() throws Exception {
        int value = 123;
        Callable<Integer> task = () -> {
            Thread.sleep(1000);
            return value;
        };

        ExecutorService executorService = newFixedThreadPool(1);
        Future<Integer> future = executorService.submit(task);
        Integer result = future.get(); // blocks

        assertTrue(result.equals(value));
    }

    @Test
    void shouldTestMultipleCallable() throws Exception {
        int N_THREADS = 16;

        Callable<Integer> task = () -> {
            Integer sleep = new Random().nextInt(9000) + 1000;
            Thread.sleep(sleep);
            return sleep;
        };

        List<Callable<Integer>> tasks = new ArrayList<>();
        for (int i = 0; i < N_THREADS; i++) {
            tasks.add(task);
        }

        ExecutorService executorService = newFixedThreadPool(N_THREADS);
        List<Future<Integer>> futures = executorService.invokeAll(tasks);

        // ...
    }

    private static void connect16Players() {
        for (int i = 0; i < 16; i++) {
            isGamePortOpen();
        }
    }

    private static boolean isGamePortOpen() {
        return isGamePortOpen(DEFAULT_PORT);
    }

    private static boolean isGamePortOpen(int port) {
        try (Socket socket = new Socket(HOST, port)) {
            socket.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
