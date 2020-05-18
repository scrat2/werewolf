package com.example.werewolf.types;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import com.example.werewolf.R;
import com.example.werewolf.general.Communication;
import com.example.werewolf.main.NightActivity;

import java.util.concurrent.Exchanger;

public class CupidonActivity extends Activity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.cupidon_panel);

        //Initiate elements of activity
        final Exchanger exchange = new Exchanger();
        Button create_validate = findViewById(R.id.button_validate_cupidon);
        final EditText lover1 = findViewById(R.id.editText_lover1);
        final EditText lover2 = findViewById(R.id.editText_lover2);

        //Button listener
        create_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Create the request
                String answer = "love_";
                answer += lover1.getText()+"_";
                answer += lover2.getText();

                //Create the thread, send the request and wait for an answer
                Thread com = new Thread(new Communication(exchange, answer));
                com.start();
                /*try {
                    answer = (String) exchange.exchange(answer);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                Intent night = new Intent(CupidonActivity.this, NightActivity.class);
                startActivity(night);
            }
        });
    }

}
