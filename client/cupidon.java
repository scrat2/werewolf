package werewolf;

/**
 *
 * @author lucas
 */
public class cupidon extends player {
    
    String lover1, lover2;
    
    public String love(){ // method for choose the lovers and linked them
        System.out.println("Which citizen will be the first lover ?");
        lover1 = lectureClavier.nextLine();
        
        System.out.println("Which citizen will be the second lover ?");
        lover2 = lectureClavier.nextLine();
        
        return lover1 + lover2;// return the two lovers
        
    }
    
}
