package com.example.panicapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class panicWidget extends AppCompatActivity {

    Button alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panic_widget);

        alert = (Button)findViewById(R.id.alertFriend);
        alert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                messageFriend();
                Intent intent = new Intent(panicWidget.this, Soothing.class);
                startActivity(intent);
            }
        });


    }

    private void messageFriend()
    {

    }

}
