package com.example.werewolf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.concurrent.Exchanger;

public class witchertest extends AppCompatActivity {

    String answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_witchertest);

        final Exchanger exchange = new Exchanger();
        Button create_validate = findViewById(R.id.button_validate_witcher);
        final EditText poisoned = findViewById(R.id.editText_poisoned);

        create_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (answer.equals("poison_") ){
                    answer += poisoned.getText();
                }
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
        public void onCheckboxClicked(View view) {
            // Is the view now checked?
            boolean checked = ((CheckBox) view).isChecked();

            switch (view.getId()) {
                case R.id.checkBox_save:
                    if (checked)
                        answer = "save";
                    break;

                case R.id.checkBox_poison:
                    if (checked)

                        answer = "poison_";

                    break;

                case R.id.checkBox_nothing:
                    if (checked)
                        answer = "nothing";
                    break;

            }


    }



}
