package pl.tmaj;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer implements Runnable {

    private static final int DEFAULT_PORT = 9191; // config server
    private static final int N_PLAYERS = 16; // config server

    private ServerSocket serverSocket;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(DEFAULT_PORT);
            for (int i = 0; i < N_PLAYERS; i++) {
                Socket player = serverSocket.accept();
                PrintWriter toSocket = new PrintWriter(player.getOutputStream(), true);
                toSocket.write("PlayerX");
                player.close();
            }
            stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }
}
