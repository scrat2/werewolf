package com.example.werewolf.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.example.werewolf.R;
import com.example.werewolf.general.GameVariables;
import com.example.werewolf.types.CupidonActivity;
import com.example.werewolf.types.SeerActivity;
import com.example.werewolf.types.WitcherActivity;
import com.example.werewolf.types.WolfActivity;
import com.example.werewolf.types.player;

import java.util.ArrayList;

public class NightActivity extends Activity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.night_panel);

        //Initiate panel elements
        TextView player_role = findViewById(R.id.night_player_role);
        final TextView current_role = findViewById(R.id.night_current_role);

        //Get needed information to the game
        final String[] order = GameVariables.getRole(); //Get an array of String with all the roles in the play order
        final int advancement = GameVariables.getAdvancement(); //Set which role is playing

        String pseudo = GameVariables.getPseudo(); //pseudo of the local player
        ArrayList<player> players = GameVariables.getPlayerList(); //List of all the player in the room
        player tmp = new player(); //Temporary player object it will be use to find the role of the local player

        //Search the role of the local player with his pseudo
        int i = 0;
        while(i<players.size() && !(tmp.pseudo.equals(pseudo))){
            tmp = players.get(i);
            i++;
        }

        //save local player in a final variable for the next
        final player me = GameVariables.getMe();

        //Add the role in the view
        player_role.append(me.role);

        //timer each 10 seconds run another role
        new CountDownTimer((order.length-advancement)*10000, 10000) {
            int i = advancement;
            //Obligatory
            public void onTick(long millisUntilFinished) {
                //Add the current player in the view
                current_role.append("\n");
                current_role.append(order[i]);

                //Check if it's the turn of the local player
                if (order[i].equals(me.role)){
                    //Set the advancement to not run the same turn again after
                    GameVariables.setAdvancement(i+1);
                    switch (me.role){
                        //Search the good role to run
                        case "seer" :
                            Intent seer = new Intent(NightActivity.this, SeerActivity.class);
                            startActivity(seer);
                            break;

                        case "cupidon":
                            Intent cupidon = new Intent(NightActivity.this, CupidonActivity.class);
                            startActivity(cupidon);
                            break;

                        case "werewolf":
                            Intent werewolf = new Intent(NightActivity.this, WolfActivity.class);
                            startActivity(werewolf);
                            break;

                        case "witch":
                            Intent witch = new Intent(NightActivity.this, WitcherActivity.class);
                            startActivity(witch);
                            break;
                    }
                }
                i++;
            }

            //Run the new activity when the timer is finish
            public void onFinish() {
                GameVariables.setAdvancement(0);
                Intent day = new Intent(NightActivity.this, DayActivity.class);
                startActivity(day);
            }
        }.start();
    }
}
