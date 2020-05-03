package werewolf;

/**
 *
 * @author lucas
 */
public class hunter extends player {
    
    String last_trophy;
    
    public String last_bullet(){ // method for kill one citizen before his dead
        System.out.println("Which citizen will be your last trophy ?");
        last_trophy = lectureClavier.nextLine();
        return last_trophy;
    }
    
}
