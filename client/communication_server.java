package werewolf;
import java.io.IOException;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class WereWolf {

 public static void main(String[] args) {
        try {
               Socket soc = new Socket(InetAddress.getByName("werewolf.sylvainboussignac.ovh"), 4444 );
               System.out.println("Adresse de l'hôte distant : " + soc.getInetAddress().getHostAddress());
               System.out.println("Réussi" );
               
        } catch (UnknownHostException e){
                e.printStackTrace();
                                
        } catch (IOException e){
                
            }
        
    }
    
}
