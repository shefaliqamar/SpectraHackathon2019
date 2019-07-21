package com.example.panicapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import com.plantronics.pdp.service.Log;
// first activity that shows up when someone enters the app
public class Login extends AppCompatActivity {
    //variables
    EditText username;
    EditText password;
    RequestQueue mqueue;
    String usernameResult;
    String passwordResult;
    String loginResult;
    Button loggedIn;
    public static String currentUserName;
    public static Integer currentUserID;
    public boolean successfullLogin = false;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Log", "in onCreate() ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loggedIn = (Button) findViewById(R.id.loggedIn);
//      You must define mqueue as a Volley request, or it will not work
        mqueue = Volley.newRequestQueue(this);

        loggedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome(v);
            }
        });
    }

    public void goToHome(View view) {
        // when called this will take you to the homeActivity where you have a menu
        Intent intent = new Intent(Login.this, Home.class);
        startActivity(intent);
    }
    //  When called, it will call jsonparse which handles login data
    public void login(View view) {
        Log.d("log", "clicked log in!");
        jsonParse();
        if(successfullLogin)
            goToHome(view);
    }


    /*  When called, jsonParse will take the username and password and check the database to see if
     *   there is a match. If there is a match, then the user will go to his home page. If there is no
     *   match, then the user will be prompted to try again.*/
    public void jsonParse() {
        // define the endpoint for the HTTP request
        String urlAddUser = "http://178.128.182.144:5000/login/";
        usernameResult = username.getText().toString();
        passwordResult = password.getText().toString();
        boolean validURL = true;
        currentUserName = usernameResult;
        // case: user cannot input an empty string
        if(usernameResult.equals("") || passwordResult.equals("") ){
            Toast.makeText(getApplicationContext(),"Login Failed, please try again",Toast.LENGTH_LONG).show();
            validURL = false;
        }
        // define the specific user and pass for the endpoint
        urlAddUser+=usernameResult + "/";
        urlAddUser+=passwordResult;
        android.util.Log.d("log", urlAddUser);
        //====================== START OF GET USER ID API REQUEST ==============================
        String urlGetUserID = "http://178.128.182.144:5000/login/";
        Log.d("log", "about to get the user id of: " +currentUserName);
        urlGetUserID +=currentUserName;
        Log.d("log", urlGetUserID);
        Log.d("log", "the prev user id was: "+ currentUserID);
        final JsonObjectRequest getUserID = new JsonObjectRequest(Request.Method.GET, urlGetUserID, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("gotUsername");
                            JSONObject obj = jsonArray.getJSONObject(0);
                            currentUserID = obj.getInt("user_id");
                            Log.d("log", "the user id of: " + currentUserName + " is: " + currentUserID);
                            if(successfullLogin == true) {
                                goToHome(null);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        // Using an object request, we will use the endpoint to request the data
        // this is only if the user did not put an empty string
        if(validURL == true) {
            JsonObjectRequest loginAPICall = new JsonObjectRequest(Request.Method.GET, urlAddUser, null,
                    new Response.Listener<JSONObject>() {
                        //if succesfull, this will run
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                // parse through the json using JSONArray and JSONObject
                                JSONArray jsonArray = response.getJSONArray("login");
                                JSONObject obj = jsonArray.getJSONObject(0);
                                loginResult = obj.getString("message");
                                Log.d("log", "the result is: " + loginResult);
//                              The json will either return the string "success" or "failure"
//                              If "success", then the user will be sent to the home screen
                                if (loginResult.equals("Success")) {
                                    Log.d("log", "yay!");
                                    successfullLogin = true;
                                    // the current user name will be saved for retrival of their information for their profile
                                    // and devices connected to their headset.
                                    currentUserName = usernameResult;
                                    // go to home screen
                                    Log.d("log", "the current logged in user is: " + currentUserName);
                                    mqueue.add(getUserID);
                                    // login failed, toast message prompts the user to try again
                                } else if (loginResult.equals("Failure")) {
                                    Log.d("log", "fail!");
                                    Toast.makeText(getApplicationContext(), "Login Failed, please try again", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                //if failed, this will run
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("log", "could not get the data using Volley");
                }
            });
            //====================== END OF LOGIN USER API REQUEST =================================
            mqueue.add(loginAPICall);
        }
    }

    /*
    public void signup(View view) {
        Intent intent = new Intent(this, signupActivity.class);
        startActivity(intent);
    }
    */
}
