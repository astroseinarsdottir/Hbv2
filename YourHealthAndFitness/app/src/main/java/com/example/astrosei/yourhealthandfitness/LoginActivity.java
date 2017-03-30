package com.example.astrosei.yourhealthandfitness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import comunicator.AppController;
import networker.UserNetworker;

public class LoginActivity extends AppCompatActivity implements UserNetworker.IDateCallback{

    private Button btnLogin;
    private EditText textUsername;
    private EditText textPassword;
    UserNetworker userNetworker = new UserNetworker(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button)findViewById(R.id.btn_Login);
        textUsername = (EditText)findViewById(R.id.text_Username);
        textPassword = (EditText)findViewById(R.id.text_Password);
        // User tries to login, redirected to HomePageActivity if successful
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                // Tries to login user.
                userNetworker.loginRequest(textUsername.getText().toString(),textPassword.getText().toString());
                //login();


            }

        });
    }

    // Tries to login user.
    public void login(String responce){

        // Check if users credentials are correct
        //userNetworker.loginRequest(textUsername.getText().toString(),textPassword.getText().toString());
        // If login successful.
        Boolean check = Boolean.valueOf(responce);
        if(check){
            Toast.makeText(getApplicationContext(),"Redirecting...",Toast.LENGTH_SHORT).show();

            // Redirect to Homepage.
            Intent intent = new Intent(LoginActivity.this, HomePageActivity.class);
            startActivity(intent);

        }
        else{
            //Else stay on this page, let the user know that login failed.
            Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
        }
    }


}
