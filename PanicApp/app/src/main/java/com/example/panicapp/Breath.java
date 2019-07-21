package com.example.panicapp;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class Breath extends AppCompatActivity {

    TextView secLeft;
    TextView breath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breath);

        secLeft = findViewById(R.id.timer);
        breath = findViewById(R.id.breath);

        new CountDownTimer(6000, 1000) {

            public void onTick(long millisUntilFinished) {

                breath.setText("Breath in");
                if(millisUntilFinished <= 3000)
                    breath.setText("Breath out");
                secLeft.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                secLeft.setText("done!");
                Intent intent = new Intent(Breath.this, Focus.class);
                startActivity(intent);
            }
        }.start();



    }

}
