package com.example.werewolf.types;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.example.werewolf.R;
import com.example.werewolf.general.Communication;
import com.example.werewolf.general.GameVariables;
import com.example.werewolf.main.NightActivity;
import com.example.werewolf.main.WaitingActivity;

import java.util.concurrent.Exchanger;

public class WolfActivity extends Activity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.wolf_panel);

        final Exchanger exchange = new Exchanger();
        Button create_validate = findViewById(R.id.button_validate_wolf);
        TextView listplayerwolf = findViewById(R.id.list_player_wolf);
        final EditText wolf = findViewById(R.id.editText_wolf);


        for (int i = 0; i < GameVariables.getNbActivePlayer(); i++) {
            player tmp = GameVariables.getPlayerList().get(i);
            listplayerwolf.append("\n" + tmp.pseudo);
        }

        create_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Create the request
                String answer = "wolf_";
                answer += wolf.getText();

                //Create the thread, send the request and wait for an answer
                Thread com = new Thread(new Communication(exchange, answer));
                com.start();
                /*try {
                    answer = (String) exchange.exchange(answer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                Intent night = new Intent(WolfActivity.this, NightActivity.class);
                startActivity(night);
            }
        });
    }
}
