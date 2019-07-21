package com.example.panicapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button emergency = (Button)findViewById(R.id.panic);
        emergency.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, panicWidget.class);
                startActivity(intent);
            }

        });

        Button addFriend = (Button)findViewById(R.id.addfriend);
        addFriend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, AddFriend.class);
                startActivity(intent);
            }
        });
    }
}
