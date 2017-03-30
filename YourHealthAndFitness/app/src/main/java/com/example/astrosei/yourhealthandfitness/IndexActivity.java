package com.example.astrosei.yourhealthandfitness;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import networker.UserNetworker;


public class IndexActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnSignUp;
    UserNetworker userNetworker = new UserNetworker();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        btnLogin = (Button)findViewById(R.id.btn_Login);
        btnSignUp = (Button)findViewById(R.id.btn_SignUp);


        // User chooses to login to his account
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.i("setonclicklistener","-------------------");
                ///userNetworker.loginRequest("http://192.168.122.1:8080/mobile_login","sol","solsol");

                Intent intent = new Intent(IndexActivity.this, LoginActivity.class);
                startActivity(intent);
            }

        });

        // User chooses to sign up to a new account
        btnSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(IndexActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

}
