package com.example.astrosei.yourhealthandfitness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText textUsername;
    private EditText textPassword;

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
                login();
            }

        });
    }

    // Tries to login user.
    public void login(){

        // Check if users credentials are correct

        // If login successful.
        if(true){
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
