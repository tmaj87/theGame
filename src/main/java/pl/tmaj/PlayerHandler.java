package pl.tmaj;

import pl.tmaj.common.Log4t;
import pl.tmaj.common.SHA256;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

import static java.time.LocalDateTime.now;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static pl.tmaj.common.Log4t.getInstanceFor;

class PlayerHandler {

    private final Log4t log4T = getInstanceFor(this);

    private final Socket player;
    private final CountDownLatch gate;
    private final String userId;
    private final ExecutorService workers = newFixedThreadPool(2);

    private ObjectOutputStream output;
    private ObjectInputStream input;
    private boolean isConnected;

    PlayerHandler(Socket player, CountDownLatch gate) {
        this.player = player;
        this.gate = gate;
        this.userId = SHA256.of(now().toString());
        initCommunication();
    }

    private void initCommunication() {
        try {
            output = new ObjectOutputStream(player.getOutputStream());
            input = new ObjectInputStream(player.getInputStream());
        } catch (IOException e) {
            log4T.WARN(e.getMessage());
        }
        handleIO();
        log4T.INFO(userId + " connected");
    }

    private void handleIO() {
        workers.submit(this::handleRead);
        workers.submit(this::handleWrite);
    }

    private void handleRead() {
        String string;
        try {
            while ((string = (String) input.readObject()) != null) {
                log4T.INFO(userId + " wrote " + string);
            }
        } catch (Exception e) {
            log4T.WARN(e.getMessage());
            closeConnection();
        }
    }

    private void handleWrite() {
        try {
            gate.await();
        } catch (Exception e) {
            log4T.WARN(e.getMessage());
        }
    }

    private boolean closeConnection() {
        return isConnected = false;
    }
}
