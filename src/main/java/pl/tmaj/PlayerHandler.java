package pl.tmaj;

import pl.tmaj.common.Log4j;
import pl.tmaj.common.SHA256;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

import static java.time.LocalDateTime.now;
import static java.util.concurrent.Executors.newFixedThreadPool;

class PlayerHandler {

    private final Log4j log4j = new Log4j(this);
    private final Socket socket;
    private final CountDownLatch gate;
    private final String userId;

    private ObjectOutputStream output;
    private ObjectInputStream input;

    PlayerHandler(Socket socket, CountDownLatch gate) {
        this.socket = socket;
        this.gate = gate;
        this.userId = SHA256.ofString(now().toString());
        initCommunication();
    }

    private void initCommunication() {
        try {
            output = new ObjectOutputStream(socket.getOutputStream());
            input = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            log4j.WARN(e.getMessage());
        }
        handleIO();
        log4j.INFO(userId + " connected");
    }

    private void handleIO() {
        Executor pool = newFixedThreadPool(2);
        pool.execute(this::handleRead);
        pool.execute(this::handleWrite);
    }

    private void handleRead() {}

    private void handleWrite() {}
}
