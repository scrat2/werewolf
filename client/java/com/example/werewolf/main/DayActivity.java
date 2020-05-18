package com.example.werewolf.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.example.werewolf.R;
import com.example.werewolf.general.Communication;
import com.example.werewolf.general.GameVariables;
import com.example.werewolf.types.player;
import java.util.concurrent.Exchanger;

public class DayActivity extends Activity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_panel);

        final Exchanger exchange = new Exchanger();
        Button create_validate = findViewById(R.id.button_validate_vote);
        TextView listplayervote = findViewById(R.id.list_player_vote);
        final EditText vote = findViewById(R.id.editText_vote);

        for (int i = 0; i < GameVariables.getNbActivePlayer(); i++) {
            player tmp = GameVariables.getPlayerList().get(i);
            listplayervote.append("\n" + tmp.pseudo);
        }

        //Button to vote
        create_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Create the request
                String answer = "vote_";
                answer += vote.getText();

                //Create the thread, send the request and wait for an answer
                Thread com = new Thread(new Communication(exchange, answer));
                com.start();
                try {
                    answer = (String) exchange.exchange(answer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        //Timer to run the night after 60 seconds
        new CountDownTimer(60000, 1000) {

            //Obligatory
            public void onTick(long millisUntilFinished) {
            }

            //Run activity when the timer is finish
            public void onFinish() {
                Intent night = new Intent(DayActivity.this, NightActivity.class);
                startActivity(night);
            }
        }.start();
    }
}
