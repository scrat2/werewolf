package werewolf;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.InetAddress;
import java.net.UnknownHostException;



/**
 *
 * @author lucas
 */
public class WereWolf {

    /**
     * @param args the command line arguments
     */
    
    private static String read() throws IOException{
        String response = "";
        int stream;
        byte[] b = new byte[4096];
        stream = reader.read(b);
        response = new String(b, 0, stream);
        return response;
        }
    
    private static PrintWriter writer = null; //Creation of the empty variable
    private static BufferedInputStream reader = null; //Creation of the empty variable
    
    public static void main(String[] args) {
        
        String contentRequest = "petite requete"; //Creation of the message which will be sent to the server
            
        try {
               Socket soc = new Socket(InetAddress.getByName("werewolf.sylvainboussignac.ovh"), 4444 ); //Connection to the server "werewolf.sylvainboussignac.ovh" on the port 4444
               System.out.println("Adresse de l'hôte distant : " + soc.getInetAddress().getHostAddress()); //Obtaining and displaying the server address
               writer = new PrintWriter(soc.getOutputStream(), true);
               reader = new BufferedInputStream(soc.getInputStream()); 
               writer.write(contentRequest);
               writer.flush();
               System.out.println(read()); //Dispalying of the message from the server
               System.out.println("Réussi"); //Displaying own message for checking the end of the program
               
        } catch (UnknownHostException e){
                e.printStackTrace();
                                
        } catch (IOException e){
                
            }
        
    }
    
}
