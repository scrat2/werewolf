package com.example.werewolf.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import com.example.werewolf.R;
import com.example.werewolf.general.Communication;
import com.example.werewolf.general.GameVariables;
import com.example.werewolf.types.player;
import java.util.concurrent.Exchanger;

//Used to create a new room
public class CreateActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_setting);

        //Initiate elements of activity
        final Exchanger exchange = new Exchanger();
        Button create_validate = findViewById(R.id.create_validate_button);
        final EditText create_player_number = findViewById(R.id.create_player_number);
        final EditText create_pseudo = findViewById(R.id.create_pseudo);


        /*Button next = findViewById(R.id.buttonNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberOfPlayer = Integer.parseInt(String.valueOf(create_player_number));
                numberOfPlayer++;
                startActivity(next);
            }
        });*/


        //Button listener
        create_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               //Initiate data
                String pseudo = "";
                String sNbPlayer ="";
                String sNbWolf = "";
                String sNbCupidon = "";
                String sNbHunter = "";
                String sNbSeer = "";
                String sNbWitch = "";
                int nbPlayer;
                pseudo += create_pseudo.getText();
                sNbPlayer += create_player_number.getText();

                switch (sNbPlayer){

                    case "5":
                        sNbWolf += "1";
                        sNbCupidon += "1";
                        sNbHunter += "1";
                        sNbSeer += "1";
                        sNbWitch += "1";
                    break;

                    case "6":
                        sNbWolf += "2";
                        sNbCupidon += "1";
                        sNbHunter += "1";
                        sNbSeer += "1";
                        sNbWitch += "1";
                        break;
                }




                /*sNbWolf += wolf_number.getText();
                sNbCupidon += cupidon_number.getText();
                sNbHunter += hunter_number.getText();
                sNbSeer += seer_number.getText();
                sNbWitch += witch_number.getText();*/
                nbPlayer = Integer.parseInt(sNbPlayer);
                player me = new player(pseudo);

                //Create the request
                String answer = "";
                String request = "";
                request = "create_"+pseudo+"_"+sNbPlayer+"_"+sNbWolf+"_"+sNbWitch+"_"+sNbSeer+"_"+sNbHunter+"_"+sNbCupidon;

                //Create the thread, send the request and wait for an answer
                Thread com = new Thread(new Communication(exchange, request));
                com.start();
                try {
                    answer = (String) exchange.exchange(answer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //Set the globals variables
                GameVariables.setRoom(answer);
                GameVariables.setPseudo(pseudo);
                GameVariables.setPlayerList(me);
                GameVariables.setNbPlayer(nbPlayer);
                GameVariables.setNbActivePlayer(1);

                //Run the setting2 activity
                Intent setting2 = new Intent(CreateActivity.this, WaitingActivity.class);
                startActivity(setting2);
            }
        });
    }
    private String request(String request, String role, int nbRole){

        return request;
    }
}
