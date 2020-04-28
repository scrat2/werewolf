package com.example.werewolf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import java.util.concurrent.Exchanger;

//Used to create a new room
public class CreateActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_panel);

        //Initiate elements of activity
        final Exchanger exchange = new Exchanger();
        Button create_validate = findViewById(R.id.create_validate_button);
        final EditText create_player_number = findViewById(R.id.create_player_number);
        final EditText create_pseudo = findViewById(R.id.create_pseudo);

        //Button listener
        create_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Create the request
                String answer = "";
                String request = "create_";
                request += create_player_number.getText()+"_";
                request += create_pseudo.getText();

                //Create the thread, send the request and wait for an answer
                Thread com = new Thread(new Communication(exchange, request));
                com.start();
                try {
                    answer = (String) exchange.exchange(answer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //Initiate and send data to the globals variables class
                String pseudo = "";
                pseudo += create_pseudo.getText();
                String sNbPlayer ="";
                sNbPlayer += create_player_number.getText();
                int nbPlayer = Integer.parseInt(sNbPlayer);
                player me = new player(pseudo);
                GameVariables.setRoom(answer);
                GameVariables.setPseudo(pseudo);
                GameVariables.setPlayerList(me);
                GameVariables.setNbPlayer(nbPlayer);
                GameVariables.setNbActivePlayer(1);

                //Run the waiting activity
                Intent Waiting = new Intent(CreateActivity.this, WaitingActivity.class);
                startActivity(Waiting);
            }
        });
    }
}
