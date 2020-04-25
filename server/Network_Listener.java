import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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

                //Create a new room
                if (enterRequestContent.contains("create")){
                    int numbPlayer = Integer.parseInt(enterRequestContent.substring(7));
                    create(numbPlayer, client);
                }
                //Join an existing room
                else if (enterRequestContent.contains("join")){
                    int room = Integer.parseInt(enterRequestContent.substring(5));
                    join(room, client);
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
    private static void create(int numbPlayer, Socket client){
        //Create the player character
        WerewolfClient player = new WerewolfClient(client);
        int roomNumber = generate();
        //Create the new Room to play
        Room room = new Room(numbPlayer, roomNumber, player);
        //Add the new room in a list during the game
        roomContainer.add(room);
        String answerContent = "Now I will create a new room to play with " + numbPlayer + " players room is available at number " + roomNumber;
        com.answer(answerContent, client);
    }

    //This method is called when someone asks to join a Room
    private static void join(int room, Socket client){
        boolean exist = false;
        WerewolfClient player = new WerewolfClient(client);
        for (int i=0; i<roomContainer.size(); i++){
            Room tmp = (Room) roomContainer.get(i);
            if (tmp.getRoomNumber() == room){
                if (!tmp.full()) {
                    tmp.join(player);
                }
                else {
                    com.answer("the room is full you can't joint this one", client);
                }
                exist = true;
            }
        }
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
