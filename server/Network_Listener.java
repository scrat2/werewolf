import java.io.IOException;
import java.net.ServerSocket;

public class Network_Listener {

    public static void main(String[] args) {

        ServerSocket server = null;
        while (true) {
            try {
                server = new ServerSocket(4444);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Port used");
            }

            try {
                server.accept();
                System.out.println("Client connection received.");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("error Connexion client.");
            }

            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
                server = null;
            }
        }

    }
}
