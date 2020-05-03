package werewolf;

import java.util.Scanner;

/**
 *
 * @author lucas
 */
public class player {
    
    
    Scanner lectureClavier = new Scanner(System.in);
    String pseudo; //name is the game
    String role; //role during the game
    String choicevote; 
    Boolean life = true;
    
    public player(){
        
    }
    
    public player(String pseudo, Boolean life){
        this.pseudo = pseudo;
        this.life = life;
    }
    
    public player(String pseudo, Boolean life, String role){
        this.pseudo = pseudo;
        this.life = life;
        this.role = role;
    }
    
    public String vote(){ //method for vote with the citizen during the day
        System.out.println("Which citizen do you want eliminate ?");
        choicevote = lectureClavier.nextLine();
        return choicevote;
    }
            
}
