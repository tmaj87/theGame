package pl.tmaj.common;

import pl.tmaj.PlayerHandler;

import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

import static pl.tmaj.common.TestConstants.DEFAULT_PORT;
import static pl.tmaj.common.TestConstants.LOCALHOST;
import static pl.tmaj.common.TestConstants.SIXTEEN_PLAYERS;

public class TestUtils {

    public static Socket getSocket(int port) {
        Socket socket;
        try {
            socket = new Socket(LOCALHOST, port);
        } catch (Exception e) {
            e.printStackTrace();
            socket = null;
        }
        return socket;
    }

    public static boolean connectToDefaultPort() {
        return getSocket(DEFAULT_PORT) != null;
    }

    public static boolean connectToPort(int port) {
        return getSocket(port) != null;
    }

    public static void mockConnections(int number) {
        for (int i = 0; i < number; i++) {
            connectToDefaultPort();
        }
    }

    public static Set<String> getSetOfSixteenPlayers() {
        Set<String> playerIds = new HashSet<>();
        for (int i = 0; i < SIXTEEN_PLAYERS; i++) {
            playerIds.add(getNewPlayerId());
        }
        return playerIds;
    }

    public static String getNewPlayerId() {
        return new PlayerHandler(getSocket(DEFAULT_PORT)).getId();
    }
}
