import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

//this object is used for communication with the client
public class Communication {

    public Communication() {
    }

    //Reader method translate byte in string
    public String read(Socket client) throws IOException {
        System.out.println("Client connection received.");
        System.out.println("Client IP : "+ client.getInetAddress());
        BufferedInputStream enterRequest = new BufferedInputStream(client.getInputStream());
        String response = "";
        int stream;
        byte[] b = new byte[4096];
        stream = enterRequest.read(b);
        response = new String(b, 0, stream);
        return response;
    }

    //answers to the client
    public void answer(String answerContent, Socket client){
        try {
            PrintWriter answer = new PrintWriter(client.getOutputStream(), true);
            answer.write(answerContent);
            answer.flush();
            System.out.println("Answer correctly send : " + answerContent);
        }catch (IOException e){
            e.printStackTrace();
            e.getMessage();
            System.out.println("Error during the answer");
        }
    }
}
