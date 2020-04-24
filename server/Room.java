//Contains all the players during the game
public class Room {
    //Room data
    private  int roomNumber;
    private int playerNumb;
    private WerewolfClient[] clientTab;

    //Default constructor
    public Room() {
    }

    public Room(int playerNumb, int roomNumber, WerewolfClient client){
        this.playerNumb = playerNumb;
        this.roomNumber = roomNumber;
        clientTab = new WerewolfClient[playerNumb];
        clientTab[0] = client;
    }
}
