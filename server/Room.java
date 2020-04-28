//Contains all the players during the game
public class Room implements Runnable{
    //Room data
    private  int roomNumber;
    private int playerNumb;
    private int activePlayer = 0;
    private WerewolfClient[] playerTab;
    //communicator
    private Communication com = new Communication();

    //Default constructor
    public Room() {
    }

    public Room(int playerNumb, int roomNumber, WerewolfClient player){
        this.playerNumb = playerNumb;
        this.roomNumber = roomNumber;
        playerTab = new WerewolfClient[playerNumb];
        playerTab[0] = player;
        activePlayer++;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    //Add a new player in the room
    public void join(WerewolfClient player){
        playerTab[activePlayer] = player;
        activePlayer++;
        com.answer("success_"+playerNumb+"_"+activePlayer, player.soc);
    }

    //Check if the room is full
    public boolean full(){
        if (activePlayer >= playerNumb){
            return true;
        }
        else {
            return false;
        }
    }

    //Doesn't work yet
    //Should send the player list and the beginning signal when the room is full
    @Override
    public void run() {
        for(int i=0;i<activePlayer;i++) {
            for (int j = 0; j < activePlayer; i++) {
                com.answer(playerTab[j].pseudo, playerTab[i].soc);
            }
        }
        if (full()){
            for (int i = 0; i<playerNumb;i++){
                com.answer("start",playerTab[i].soc);
            }
        }
    }
}
