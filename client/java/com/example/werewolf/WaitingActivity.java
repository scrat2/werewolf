package com.example.werewolf;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

//Run while waiting for the room is full
public class WaitingActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting_panel);

        //Initiate elements of activity
        TextView title = findViewById(R.id.waiting_room_number);
        TextView playersList = findViewById(R.id.waiting_players_list);
        TextView numberPlayer = findViewById(R.id.waiting_number_player);
        Button refresh = findViewById(R.id.waiting_refresh);

        //Fills elements
        title.append(GameVariables.getRoom());
        numberPlayer.append(GameVariables.getNbActivePlayer() + "/" + GameVariables.getNbPlayer());
        for (int i = 0; i<GameVariables.getNbActivePlayer(); i++){
            player tmp = GameVariables.getPlayerList().get(i);
            playersList.append("\n" + tmp.pseudo);
        }

        //Button listener
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Run the Synchronizer thread
                Thread sync = new Thread(new Synchronizer());
                sync.start();
                try {
                    sync.join();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                //Reload this activity
                recreate();
            }
        });
    }
}
