package com.example.werewolf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import java.util.concurrent.Exchanger;

//Used to join an existing room
public class JoinActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_panel);

        //Initiate elements of activity
        final Exchanger exchange = new Exchanger();
        Button join_validate = findViewById(R.id.join_validate_button);
        final EditText join_room = findViewById(R.id.join_room_number);
        final EditText join_pseudo = findViewById(R.id.join_pseudo);
        final TextView join_answer = findViewById(R.id.join_answer);

        //Button listener
        join_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Create the request
                String answer = "";
                String request = "join_";
                request += join_room.getText() + "_";
                request += join_pseudo.getText();

                //Create the thread, send the request and wait for an answer
                Thread com = new Thread(new Communication(exchange, request));
                com.start();
                try {
                    answer = (String) exchange.exchange(answer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //Cut the answer to treat it
                String sAnswer[] = answer.split("_");

                //Check if the request is validate by the server
                if (sAnswer[0].equals("success")){

                    //Initiate and send data to the globals variables class
                    String pseudo = "";
                    String room = "";
                    pseudo += join_pseudo.getText();
                    room += join_room.getText();
                    player me = new player(pseudo);
                    GameVariables.setRoom(room);
                    GameVariables.setPseudo(pseudo);
                    GameVariables.setPlayerList(me);
                    GameVariables.setNbPlayer(Integer.parseInt(sAnswer[1]));
                    GameVariables.setNbActivePlayer(Integer.parseInt(sAnswer[2]));

                    //Run the synchronizer thread to have the player list
                    Thread sync = new Thread(new Synchronizer());
                    sync.start();
                    try {
                        sync.join();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }

                    //Run the waiting activity
                    Intent Waiting = new Intent(JoinActivity.this, WaitingActivity.class);
                    startActivity(Waiting);
                }
                //If the server refuses the join request display why
                else {
                    join_answer.append(answer);
                }
            }
        });
    }
}
