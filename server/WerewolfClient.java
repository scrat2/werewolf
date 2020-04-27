import java.net.Socket;

//Contains player information
public class WerewolfClient {
    //Client data
    Socket soc;
    String pseudo;
    String role;

    //Default constructor
    public WerewolfClient() {
    }

    public WerewolfClient(Socket soc, String pseudo) {
        this.pseudo = pseudo;
        this.soc = soc;
    }

    public Socket getSoc() {
        return soc;
    }
}
