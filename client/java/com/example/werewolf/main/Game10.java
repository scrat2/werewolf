package com.example.werewolf.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.werewolf.R;

public class Game10 extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game10);

        //Run the create activity
        final Button info = findViewById(R.id.buttonInfo);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent info = new Intent(Game10.this, PresentationRoles.class);
                startActivity(info);
            }
        });
    }
    public void ShowPopup(View view) {
    }
}
