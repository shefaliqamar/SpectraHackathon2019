package com.example.panicapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.MessageCreator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;



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
        String url = "https://mulberry-chough-5749.twil.io/joke";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

}
