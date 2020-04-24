import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Network_Listener {

    //Room container
    private static ArrayList roomContainer = new ArrayList();

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
                    create(numbPlayer, client);
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
                //answer("received 5/5", client);

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

    //answers to the client
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

    //This method is called when someone asks to create a Room
    private static void create(int numbPlayer, Socket client){
        //Create the player character
        WerewolfClient wc = new WerewolfClient(client);
        int roomNumber = generate();
        //Create the new Room to play
        Room room = new Room(numbPlayer, roomNumber, wc);
        //Add the new room in a list during the game
        roomContainer.add(room);
        String answerContent = "Now I will create a new room to play with " + numbPlayer + " players room is available at number " + roomNumber;
        answer(answerContent, client);
    }

    //This method is called when someone asks to join a Room
    private static void join(int room){

        System.out.println("Now I will join the room " + room + " to play");
    }

    //Generates a random number for the Room ID
    private static int generate(){
        int number = 100 + (int)(Math.random() * 100);
        return number;
    }
}
