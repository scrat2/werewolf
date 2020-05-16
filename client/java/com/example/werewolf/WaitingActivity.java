package com.example.werewolf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
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
        for (int i = 0; i < GameVariables.getNbActivePlayer(); i++) {
            player tmp = GameVariables.getPlayerList().get(i);
            playersList.append("\n" + tmp.pseudo);
        }

        //Check if the game can start or not
        if (!GameVariables.getGameStatus().equals("start")) {
            //Counter to refresh activity every 2 seconds
            new CountDownTimer(2000, 1000) {

                //Obligatory
                public void onTick(long millisUntilFinished) {
                }

                //Refresh the activity when the timer is finish
                public void onFinish() {
                    //Run the Synchronizer thread
                    Thread sync = new Thread(new Synchronizer());
                    sync.start();
                    //Wait for the end of the thread
                    try {
                        sync.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //Refresh the activity
                    finish();
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
                }
            }.start();
        }
        //Run the new activity
        else {
            Intent play = new Intent(WaitingActivity.this, GameActivity.class);
            startActivity(play);
        }
    }
}
