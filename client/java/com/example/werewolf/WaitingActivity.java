package com.example.werewolf;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import java.util.concurrent.Exchanger;

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

        //Create the interface's elements (Doesn't work yet)
        numberPlayer.append(GameVariables.getNbActivePlayer() + "/" + GameVariables.getNbPlayer());
        title.append(GameVariables.getRoom());
        //final Exchanger exchange = new Exchanger();
        playersList.append("\n" + GameVariables.getPseudo());
        //Thread com = new Thread(new Communication(exchange));
        //String answer = "";
        int nbPlayer = GameVariables.getNbPlayer();
    }

    //Reload the activity
    public void reload(){
        recreate();
    }
}
