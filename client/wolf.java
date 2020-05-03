package werewolf;

/**
 *
 * @author lucas
 */
public class wolf extends player{
    
    String devoured_person;
    
    public String kill(){ // method for kill during the night
        System.out.println("Which citizen want you for the dinner ?");
        devoured_person = lectureClavier.nextLine();
        return devoured_person;
    }
}
