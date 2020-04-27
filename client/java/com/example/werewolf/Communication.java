package com.example.werewolf;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Exchanger;

public class Communication implements Runnable {
    private static Socket soc;
    private static Exchanger exchange;

    public Communication(Exchanger ex){
        exchange = ex;
    }

    public void run() {

        try {


            String sendRequest ="";

            soc = new Socket(InetAddress.getByName("werewolf.sylvainboussignac.ovh"), 4444); //Connection to the server "werewolf.sylvainboussignac.ovh" on the port 4444
            sendRequest = (String) exchange.exchange(sendRequest);
            answer(sendRequest);
            exchange.exchange(read());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            e.getMessage();
        }
    }
    
    //Reader method translate byte in string
    public String read() throws IOException {
        BufferedInputStream enterRequest = new BufferedInputStream(soc.getInputStream());
        String response = "";
        int stream;
        byte[] b = new byte[4096];
        stream = enterRequest.read(b);
        response = new String(b, 0, stream);
        return response;
    }

    //answers to the client
    public static void answer(String answerContent){
        try {
            PrintWriter answer = new PrintWriter(soc.getOutputStream(), true);
            answer.write(answerContent);
            answer.flush();
        }catch (IOException e){
            e.printStackTrace();
            e.getMessage();
        }
    }
}
