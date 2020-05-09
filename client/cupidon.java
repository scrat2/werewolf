package werewolf;

/**
 *
 * @author lucas
 */
public class cupidon extends player {
    
        
    public String[] love(){ // method for choose the lovers and linked them
        
        String[] lovers= new String[2];
        
        System.out.println("Which citizen will be the first lover ?");
        lovers[0] = lectureClavier.nextLine();
        
        System.out.println("Which citizen will be the second lover ?");
        lovers[2] = lectureClavier.nextLine();
        
        return lovers;// return the two lovers
        
    }
    
}
