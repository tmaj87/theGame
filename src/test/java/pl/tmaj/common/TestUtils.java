package pl.tmaj.common;

import java.net.Socket;

public class TestUtils {

    public static boolean connectToPort(int port) {
        try (Socket socket = new Socket("localhost", port)) {
            socket.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
