import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

//Main method run in an infinite loop to listen the network
public class Network_Listener {

    //Room container
    private static ArrayList<Room> roomContainer = new ArrayList<Room>();
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

                String pseudo;
                int room;
                switch (request[0]){

                    //Create a new room
                    case "create":
                        pseudo = request[1];
                        int numbPlayer = Integer.parseInt(request[2]);
                        int numbWolf =  Integer.parseInt(request[3]);
                        int numbWitch =  Integer.parseInt(request[4]);
                        int numbSeer =  Integer.parseInt(request[5]);
                        int numbHunter =  Integer.parseInt(request[6]);
                        int numbCupidon =  Integer.parseInt(request[7]);
                        int numbVillager = numbPlayer - numbWolf - numbWitch - numbSeer - numbHunter - numbCupidon;
                        create(numbPlayer, client, pseudo, numbWolf, numbWitch, numbVillager, numbSeer, numbHunter, numbCupidon);
                        break;

                    //Join an existing room
                    case "join":
                        room = Integer.parseInt(request[1]);
                        pseudo = request[2];
                        join(room, client, pseudo);
                        break;

                    //Ask to refresh the player list
                    case "refresh":
                        room = Integer.parseInt(request[1]);
                        pseudo = request[2];
                        Room tmp = check_room(room);
                        if (tmp.getActivePlayer()>0){
                            //Get the player list and send the result
                            String answer = tmp.wait_room(pseudo);
                            com.answer(answer, client);
                        }
                        break;

                    //Reject wrongs requests
                    default:
                        System.out.println("Invalid request");
                        break;
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
    private static void create(int numbPlayer, Socket client, String pseudo, int numbWolf, int numbWitch, int numbVillager, int numbSeer, int numbHunter, int numbCupidon){
        //Create the player character
        WerewolfClient player = new WerewolfClient(client, pseudo);
        int roomNumber = generate();
        //Create the new Room to play
        Room room = new Room(numbPlayer, roomNumber, player, numbWolf, numbWitch, numbVillager, numbSeer, numbHunter, numbCupidon);
        //Add the new room in a list during the game
        roomContainer.add(room);
        String answerContent = Integer.toString(roomNumber);
        com.answer(answerContent, client);
    }

    //This method is called when someone asks to join a Room
    private static void join(int room, Socket client, String pseudo){
        //Creates the client who sends the request
        WerewolfClient player = new WerewolfClient(client, pseudo);

        //Creates the temporary room
        Room tmp = check_room(room);

        //Check if the room exist
        if (tmp.getActivePlayer()>0){
            //Check if the room is full
            if (!tmp.full()) {
                tmp.join(player);
                //tmp.wait_room(pseudo);
            }
            else {
                com.answer("the room is full you can't joint this one", client);
            }
        }
        else {
            com.answer("the room doesn't exist", client);
        }
    }

    //Generates a random number for the Room ID
    private static int generate(){
        int number = 100 + (int)(Math.random() * 100);
        return number;
    }

    //Check if the room asked exists
    private static Room check_room(int room){
        for (int i=0; i<roomContainer.size(); i++){
            Room tmp = (Room) roomContainer.get(i);
            //If the room is found
            if (tmp.getRoomNumber() == room){
                return tmp;
            }
        }
        return new Room();
    }
}
