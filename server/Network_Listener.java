import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

//Main method run in an infinite loop to listen the network
public class Network_Listener {

    //Room container
    private static ArrayList roomContainer = new ArrayList();
    //communicator
    private static Communication com = new Communication();

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
                String enterRequestContent = com.read(client);
                System.out.println(enterRequestContent);
                String request[] = enterRequestContent.split("_");

                //Create a new room
                if (request[0].equals("create")){
                    int numbPlayer = Integer.parseInt(request[1]);
                    String pseudo = request[2];
                    create(numbPlayer, client, pseudo);
                }

                //Join an existing room
                else if (request[0].equals("join")){
                    int room = Integer.parseInt(request[1]);
                    String pseudo = request[2];
                    join(room, client, pseudo);
                }

                //Reject wrongs requests
                else {
                    System.out.println("Invalid request");
                }

            } catch (IOException e) {
                e.printStackTrace();
                e.getMessage();
                System.out.println("error Connexion client.");

            } catch (NumberFormatException e){
                System.out.println("bad request format");
                com.answer("please don't modify request", client);
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

    //This method is called when someone asks to create a Room
    private static void create(int numbPlayer, Socket client, String pseudo){
        //Create the player character
        WerewolfClient player = new WerewolfClient(client, pseudo);
        int roomNumber = generate();
        //Create the new Room to play
        Room room = new Room(numbPlayer, roomNumber, player);
        //Add the new room in a list during the game
        roomContainer.add(room);
        String answerContent = Integer.toString(roomNumber);
        com.answer(answerContent, client);
    }

    //This method is called when someone asks to join a Room
    private static void join(int room, Socket client, String pseudo){
        boolean exist = false;

        //Creates the client who sends the request
        WerewolfClient player = new WerewolfClient(client, pseudo);

        //Check if the room asked exists
        for (int i=0; i<roomContainer.size(); i++){
            Room tmp = (Room) roomContainer.get(i);

            //If the room is found
            if (tmp.getRoomNumber() == room){
                //Check if the room is full
                if (!tmp.full()) {
                    tmp.join(player);
                }
                else {
                    com.answer("the room is full you can't joint this one", client);
                }
                exist = true;
            }
        }
        //If the room doesn't exist
        if (!exist){
            com.answer("The room doesn't exist", client);
        }
    }

    //Generates a random number for the Room ID
    private static int generate(){
        int number = 100 + (int)(Math.random() * 100);
        return number;
    }
}
