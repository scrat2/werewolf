package com.example.werewolf;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Exchanger;

public class Communication implements Runnable {
    private static Socket soc;
    private Exchanger exchange;
    private String request;

    //Constructor to send a request
    public Communication(Exchanger ex, String request){
        exchange = ex;
        this.request = request;
    }

    //Constructor to read only a request
    public Communication(Exchanger exchange) {
        this.exchange = exchange;
    }

    //This method will be run with a new thread
    public void run() {
        try {

            //Create socket to communicate with the server
            soc = new Socket(InetAddress.getByName("werewolf.sylvainboussignac.ovh"), 4444);

            //Check which constructor has been used and in case of needing send the request
            if (request != null) {
                answer(request);
            }

            //Read the request from the server and send it to the caller
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
