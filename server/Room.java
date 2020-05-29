import java.util.ArrayList;

//Contains all the players during the game
public class Room {
    //Room data
    private  int roomNumber;
    private int playerNumb;
    private int activePlayer = 0;
    private WerewolfClient[] playerTab;
    private ArrayList<String> roleList = new ArrayList<String>();
    //communicator
    private Communication com = new Communication();

    //Default constructor
    public Room() {
    }

    public Room(int playerNumb, int roomNumber, WerewolfClient player, int numbWolf, int numbWitch, int numbVillager, int numbSeer, int numbHunter, int numbCupidon){
        this.playerNumb = playerNumb;
        this.roomNumber = roomNumber;
        roleList(numbWolf, numbWitch, numbVillager, numbSeer, numbHunter, numbCupidon);
        playerTab = new WerewolfClient[playerNumb];
        playerTab[0] = player;
        activePlayer++;
        System.out.println("In this room we found : ");
        for (int i = 0; i<roleList.size(); i++){
            System.out.println(roleList.get(i));
        }
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getActivePlayer() {
        return activePlayer;
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

    //Send the player list and the beginning signal when the room is full
    public String wait_room(){
        String answer = "";
        for (int j = 0; j < activePlayer; j++) {
            answer += playerTab[j].pseudo + "_";
        }
        if (full()){
            for (int i = 0; i<playerNumb;i++){
                answer = "start";
            }
        }
        return answer;
    }

    //Create roles list
    private void roleList( int numbWolf, int numbWitch, int numbVillager, int numbSeer, int numbHunter, int numbCupidon){
        for (int i = 0; i<numbWolf; i++){
            roleList.add("werewolf");
        }
        for (int i = 0; i<numbWitch; i++){
            roleList.add("witch");
        }
        for (int i = 0; i<numbVillager; i++){
            roleList.add("Villager");
        }
        for (int i = 0; i<numbSeer; i++){
            roleList.add("seer");
        }
        for (int i = 0; i<numbHunter; i++){
            roleList.add("hunter");
        }
        for (int i = 0; i<numbCupidon; i++){
            roleList.add("cupidon");
        }
    }
}
