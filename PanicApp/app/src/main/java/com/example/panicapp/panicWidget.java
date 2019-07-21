package com.example.panicapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import com.twilio.rest.api.v2010.account.Message;


public class panicWidget extends AppCompatActivity {

    Button alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panic_widget);

        alert = (Button)findViewById(R.id.alertFriend);
        alert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //messageFriend();
                Intent intent = new Intent(panicWidget.this, Soothing.class);
                startActivity(intent);
            }
        });


    }

    private void messageFriend()
    {
        /*String url = "https://yourdomain.twil.io/joke";
        StringRequest request = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("APP", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("APP", error.getLocalizedMessage());
                    }
                }
        );
        Volley.newRequestQueue(context).add(request); */

        final String ACCOUNT_SID = "ACe977c90ea5e3bfeb2534e1df1a9af9ce";
        final String AUTH_TOKEN =  "2046323e3f657aca222bd96b6f142b25";

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
        new PhoneNumber(System.getenv("MY_PHONE_NUMBER")),
                new PhoneNumber("+4086470140"),

                "HELL O IT WORKS OMG"

        ).create();

        System.out.println(message.getSid());
    }

}
