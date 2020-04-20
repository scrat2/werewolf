import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Network_Listener {
    //Create request reader and answerer
    private static BufferedInputStream enterRequest = null;
    private static PrintWriter answer = null;

    public static void main(String[] args) {
        //create sockets
        ServerSocket server = null;
        Socket client = null;

        //Run the Network Listener
        while (true) {
            //try to create the socket on port 4444
            try {
                server = new ServerSocket(4444);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Port used");
            }

            //Wait for a connexion read the request and answer to this one
            try {
                //wait
                client = server.accept();

                //Connection received and read request
                System.out.println("Client connection received.");
                System.out.println("Client IP : "+ client.getInetAddress());
                enterRequest = new BufferedInputStream(client.getInputStream());
                String enterRequestContent = read();
                System.out.println(enterRequestContent);

                //answer to this request
                answer = new PrintWriter(client.getOutputStream(), true);
                answer.write("reçu 5/5");
                answer.flush();
                System.out.println("réponse correctement envoyé");
            } catch (IOException e) {
                e.printStackTrace();
                e.getMessage();
                System.out.println("error Connexion client.");
            }

            //close the socket to wait an other connection
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
                server = null;
            }
        }
    }

    //Reader method translate byte in string
    private static String read() throws IOException{
        String response = "";
        int stream;
        byte[] b = new byte[4096];
        stream = enterRequest.read(b);
        response = new String(b, 0, stream);
        return response;
    }
}
