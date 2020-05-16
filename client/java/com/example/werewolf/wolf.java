package com.example.werewolf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.Exchanger;

public class wolf extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wolf);

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
                try {
                    answer = (String) exchange.exchange(answer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }



        });
    }
}
