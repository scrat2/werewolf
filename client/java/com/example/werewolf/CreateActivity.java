package com.example.werewolf;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import java.util.concurrent.Exchanger;

public class CreateActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.create_panel);
        final Exchanger exchange = new Exchanger();
        Button create_validate = findViewById(R.id.create_validate_button);
        create_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread com = new Thread(new Communication(exchange));
                com.start();
                final EditText create_player_number = findViewById(R.id.create_player_number);
                final EditText create_pseudo = findViewById(R.id.create_pseudo);
                TextView create_answer = findViewById(R.id.create_answer);
                String answer = "";
                String request = "create_";
                request += create_player_number.getText()+"_";
                request += create_pseudo.getText();
                try {
                    exchange.exchange(request);
                    answer = (String) exchange.exchange(answer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                create_answer.append(answer);
            }
        });
    }
}
