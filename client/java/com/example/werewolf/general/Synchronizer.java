package com.example.werewolf.general;

import com.example.werewolf.types.player;

import java.util.concurrent.Exchanger;

public class Synchronizer implements Runnable{

    @Override
    public void run() {

        //Send a request to the server to get the players list
        Exchanger exchange = new Exchanger();
        String answer = "";
        String request = "refresh_" + GameVariables.getRoom() + "_" + GameVariables.getPseudo();
        Thread com = new Thread(new Communication(exchange, request));
        com.start();
        try {
            answer = (String) exchange.exchange(answer);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //reset variables
        GameVariables.setNbActivePlayer(0);
        GameVariables.clearPlayerList();

        //Check if the game can start or not
        String finalAnswer[] = answer.split("_");
        if (!finalAnswer[0].equals("start")) {
            //Add all the player send by the server to the GameVariable players tab
            for (int i = 0; i < finalAnswer.length; i++) {
                player tmp = new player(finalAnswer[i]);
                GameVariables.setPlayerList(tmp);
                GameVariables.setNbActivePlayer(GameVariables.getNbActivePlayer() + 1);
            }
        }

        //Set the Game status to start to signal the game can start
        else {
            GameVariables.setMe(GameVariables.getPseudo(), finalAnswer[1]);
            GameVariables.setGameStatus(finalAnswer[0]);
        }
    }
}
