import java.io.IOException;
import java.net.Socket;

public class CheckAppiumStatus {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 4723;

        try (Socket socket = new Socket(host, port)) {
            System.out.println("✓ SUCCESS: Appium is running on " + host + ":" + port);
        } catch (IOException e) {
            System.out.println("✗ FAILED: Appium is NOT running on " + host + ":" + port);
            System.out.println("Error: " + e.getMessage());
        }
    }
}

