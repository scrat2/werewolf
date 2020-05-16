package com.example.werewolf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.Exchanger;

public class huntertest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huntertest);

        //Initiate elements of activity
        final Exchanger exchange = new Exchanger();
        Button create_validate = findViewById(R.id.button_validate_hunter);
        final EditText hunt = findViewById(R.id.editText_hunter);

        //Button listener
        create_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Create the request
                String answer = "lastbullet_";
                answer += hunt.getText();

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
