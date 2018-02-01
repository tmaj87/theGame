package pl.tmaj.common;

import java.net.Socket;

import static pl.tmaj.common.TestConstants.DEFAULT_PORT;
import static pl.tmaj.common.TestConstants.LOCALHOST;

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

}
