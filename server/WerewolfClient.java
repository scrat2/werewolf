import java.net.Socket;

//Contains player information
public class WerewolfClient {
    //Client data
    Socket soc;
    String role;

    //Default constructor
    public WerewolfClient() {
    }

    public WerewolfClient(Socket soc) {
        this.soc = soc;
    }
}
