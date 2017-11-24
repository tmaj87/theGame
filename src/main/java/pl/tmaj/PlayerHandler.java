package pl.tmaj;

import pl.tmaj.common.Logable;
import pl.tmaj.common.SHA256;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

import static java.time.LocalDateTime.now;
import static java.util.concurrent.Executors.newFixedThreadPool;

class PlayerHandler extends Logable {

    private final Socket player;
    private final CountDownLatch gate;
    private final String userId;

    private ObjectOutputStream output;
    private ObjectInputStream input;

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

    private void handleWrite() {
        try {
            gate.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
