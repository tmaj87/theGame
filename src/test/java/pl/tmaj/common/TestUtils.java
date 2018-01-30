package pl.tmaj.common;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    private static final String LOCALHOST = "localhost";
    private static List<Socket> acquiredConnection = new ArrayList<>();

    public static Socket getSocket(int port) {
        Socket socket;
        try {
            socket = new Socket(LOCALHOST, port);
            return socket;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean connectToPort(int port) {
        try (Socket socket = new Socket(LOCALHOST, port)) {
            acquiredConnection.add(socket); // gc lul;
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
