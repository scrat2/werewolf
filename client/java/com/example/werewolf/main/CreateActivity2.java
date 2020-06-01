package com.example.werewolf.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.werewolf.R;

public class CreateActivity2 extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_setting2);

        //Run the create activity
        final Button info = findViewById(R.id.buttonInfo);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent info = new Intent(CreateActivity2.this, PresentationRoles.class);
                startActivity(info);
            }
        });
    }

    public void ShowPopup(View view) {
    }
}

