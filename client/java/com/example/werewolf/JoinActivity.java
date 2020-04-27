package com.example.werewolf;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.concurrent.Exchanger;

public class JoinActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.join_panel);

        final Exchanger exchange = new Exchanger();
        Button join_validate = findViewById(R.id.join_validate_button);
        join_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread com = new Thread(new Communication(exchange));
                com.start();
                final EditText join_room = findViewById(R.id.join_room_number);
                final EditText join_pseudo = findViewById(R.id.join_pseudo);
                TextView join_answer = findViewById(R.id.join_answer);
                String answer = "";
                String request = "join_";
                request += join_room.getText() + "_";
                request += join_pseudo.getText();
                try {
                    exchange.exchange(request);
                    answer = (String) exchange.exchange(answer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                join_answer.append(answer);
            }
        });
    }
}
