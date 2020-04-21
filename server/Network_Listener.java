import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Network_Listener {

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
                String enterRequestContent = read(client);
                System.out.println(enterRequestContent);

                //Create a new room
                if (enterRequestContent.contains("create")){
                    int numbPlayer = Integer.parseInt(enterRequestContent.substring(7));
                    create(numbPlayer);
                }
                //Join an existing room
                else if (enterRequestContent.contains("join")){
                    int room = Integer.parseInt(enterRequestContent.substring(5));
                    join(room);
                }
                //Reject wrongs requests
                else {
                    System.out.println("Invalid request");
                }

                //Answer
                answer("received 5/5", client);

            } catch (IOException e) {
                e.printStackTrace();
                e.getMessage();
                System.out.println("error Connexion client.");
            } catch (NumberFormatException e){
                System.out.println("bad request format");
                answer("please don't modify request", client);
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
    private static String read(Socket client) throws IOException{
        System.out.println("Client connection received.");
        System.out.println("Client IP : "+ client.getInetAddress());
        BufferedInputStream enterRequest = new BufferedInputStream(client.getInputStream());
        String response = "";
        int stream;
        byte[] b = new byte[4096];
        stream = enterRequest.read(b);
        response = new String(b, 0, stream);
        return response;
    }

    //answer to the client
    private static void answer(String answerContent, Socket client){
        try {
            PrintWriter answer = new PrintWriter(client.getOutputStream(), true);
            answer.write(answerContent);
            answer.flush();
            System.out.println("Answer correctly send");
        }catch (IOException e){
            e.printStackTrace();
            e.getMessage();
            System.out.println("Error during the answer");
        }
    }

    private static void create(int numbPlayer){
        System.out.println("Now I will create a new room to play with " + numbPlayer + " players");
    }

    private static void join(int room){
        System.out.println("Now I will join the room " + room + " to play");
    }
}
