package com.example.werewolf.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import androidx.annotation.Nullable;

import com.example.werewolf.R;
import com.example.werewolf.general.GameVariables;
import com.example.werewolf.general.Synchronizer;
import com.example.werewolf.types.player;

//Run while waiting for the room is full
public class WaitingActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiting_queue);

        //Initiate elements of activity
        TextView title = findViewById(R.id.waiting_room_number);
        TextView playersList = findViewById(R.id.waiting_players_list);
        TextView numberPlayer = findViewById(R.id.waiting_number_player);

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

            switch (GameVariables.getNbPlayer()){
                case 5:
                    Intent game5 = new Intent(WaitingActivity.this, Game5.class);
                    startActivity(game5);
                break;

                case 8:
                    Intent game8 = new Intent(WaitingActivity.this, Game8.class);
                    startActivity(game8);
                break;

                case 10:
                    Intent game10 = new Intent(WaitingActivity.this, Game10.class);
                    startActivity(game10);
                break;

            }

        }
    }
}
