import java.util.ArrayList;

//Contains all the players during the game
public class Room {
    //Room data
    private  int roomNumber;
    private int playerNumb;
    private int activePlayer = 0;
    private WerewolfClient[] playerTab;
    private ArrayList<String> roleList = new ArrayList<String>();
    private ArrayList<Integer> voteList = new ArrayList<>();
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
    }

    //Getter
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
    public String wait_room(String pseudo){
        String answer = "";
        for (int j = 0; j < activePlayer; j++) {
            answer += playerTab[j].pseudo + "_";
        }
        if (full()){

            int playerIndex = find_player(pseudo);

            //It is if he is not in the room that should not happen just a precaution
            if(playerIndex >= playerTab.length){
                answer = "error";
            }
            else {
                //give role when the room is full
                distribution(playerIndex);
                answer = "start_" + playerTab[playerIndex].role;
            }
        }
        return answer;
    }

    //search the player who is asking to send him his role
    private int find_player(String pseudo){
        int i = 0;
        while(!playerTab[i].pseudo.equals(pseudo) && i < playerTab.length){
            i++;
        }
        return i;
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

    //Role distribution
    private void distribution(int i){
        int roleIndex;
        roleIndex = (int) (Math.random() * roleList.size());
        System.out.println("we choose the : "+roleIndex);
        playerTab[i].role = roleList.get(roleIndex);
        roleList.remove(roleIndex);
    }

    //Action method
    public void kill(String target){
        int playerIndex = find_player(target);
        com.answer("die",playerTab[playerIndex].soc);
    }

    public void save(String target){
        int playerIndex = find_player(target);
        com.answer("save",playerTab[playerIndex].soc);
    }

    public void love(String target1, String target2){
        int playerIndex1 = find_player(target1);
        int playerIndex2 = find_player(target2);
        com.answer("love_"+playerTab[playerIndex2].pseudo, playerTab[playerIndex1].soc);
        com.answer("love_"+playerTab[playerIndex1].pseudo, playerTab[playerIndex2].soc);
    }

    public void watch(String pseudo, String target){
        int pseudoIndex = find_player(target);
        int targetIndex = find_player(pseudo);
        com.answer(playerTab[targetIndex].role, playerTab[pseudoIndex].soc);
    }

    public void vote(String target){
        int playerIndex = find_player(target);
        voteList.add(playerIndex);
    }

    public void countVote(){
        int counter = 0;
        int max = 0;
        int search;
        int eliminate = 0;
        while(!voteList.isEmpty()){
            search = voteList.get(0);
            for(int i = 0; i<voteList.size(); i = i){
                if(search==voteList.get(i)){
                    counter++;
                    voteList.remove(i);
                }
                else {
                    i++;
                }
            }
            if (counter > max){
                eliminate = search;
                max = counter;
            }
            counter = 0;
        }
        for(int i = 0; i < playerTab.length; i++){
            com.answer(playerTab[eliminate].pseudo, playerTab[i].soc);
        }
    }
}
