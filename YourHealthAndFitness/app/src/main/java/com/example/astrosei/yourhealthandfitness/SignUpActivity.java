package com.example.astrosei.yourhealthandfitness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

import networker.UserNetworker;
import sessions.SessionManager;

public class SignUpActivity extends AppCompatActivity implements UserNetworker.registerCallback{

    private Spinner spinnerGoal;
    private Spinner spinnerGender;
    private EditText textName;
    private EditText textEmail;
    private EditText textUsername;
    private EditText textPassword;
    private EditText textAge;
    private EditText textWeight;
    private Button btnSignUp;
    private String[] goals;
    private String[] genders;
    private ArrayAdapter<String> goalAdapter;
    private ArrayAdapter<String> genderAdapter;

    UserNetworker userNetworker = new UserNetworker(this);
    SessionManager session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        spinnerGoal = (Spinner)findViewById(R.id.spinner_Goal);
        spinnerGender = (Spinner)findViewById(R.id.spinner_Gender);
        textName = (EditText)findViewById(R.id.text_Name);
        textEmail = (EditText)findViewById(R.id.text_Email);
        textUsername = (EditText)findViewById(R.id.text_Username);
        textPassword = (EditText)findViewById(R.id.text_Password);
        textAge = (EditText)findViewById(R.id.text_Age);
        textWeight = (EditText)findViewById(R.id.text_Weight);
        btnSignUp = (Button)findViewById(R.id.btn_SignUp);

        goals = new String[]{"stronger","buffer", "leaner"};
        genders = new String[]{"male", "female"};

        // Create an ArrayAdapter and add the list of goals to it.
        goalAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, goals);
        genderAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genders);

        // Set title for the spinner in the UI.
        spinnerGoal.setPrompt("Choose your goal");
        spinnerGender.setPrompt("Choose your gender");

        // Apply the adapter to the spinner so the user can view the list of goals.
        spinnerGoal.setAdapter(goalAdapter);
        spinnerGender.setAdapter(genderAdapter);

        // Set layout so that the dropdown is not so dense.
        goalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Do something when a goal is chosen.
        spinnerGoal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        // Do something when a gender is chosen.
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        // User tries to sign up, redirected to HomePageActivity if successful.
        btnSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                // Tries to signup user.
                signup();
            }
        });

    }
    // Validation for sign up
    public boolean validate(){

        String name = textName.getText().toString();
        String username = textUsername.getText().toString();
        String password = textPassword.getText().toString();
        String email = textEmail.getText().toString();
        String age = textAge.getText().toString();
        String weight = textWeight.getText().toString();
        String goal = spinnerGoal.getSelectedItem().toString();

        boolean validated = true;

        if(name.isEmpty() ||  name.length()<2) {
            textName.setError("Name must be at least 3 letters");
            validated = false;
        }
        if(name.length()>30){
            textName.setError("Name can't be longer than 30 letters");
            validated = false;
        }
       if(username.length()<1){
            textUsername.setError("Username must be at least 2 letters");
            validated = false;
        }
        if(password.length()<6){
            textPassword.setError("Password must be at least 7 letters");
            validated = false;
        }
        if(email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            textEmail.setError("Email is not valid");
            validated = false;
        }
        if(age.isEmpty()){
            textAge.setError("Age should not be empty");
            validated = false;
        }
        if(weight.isEmpty()){
            textWeight.setError("Weight should not be empty");
            validated = false;
        }
        return validated;
    }

    // Tries to signup user
    public void signup(){

        //If the sign up is not in the correct format, let the user know that sign up failed.
        if(!validate()){
            Toast.makeText(getApplicationContext(), "Sign up failed",Toast.LENGTH_SHORT).show();
            return;
        }

        String name = textName.getText().toString();
        String username = textUsername.getText().toString();
        String password = textPassword.getText().toString();
        String email = textEmail.getText().toString();
        String age = textAge.getText().toString();
        String weight = textWeight.getText().toString();
        String goal = spinnerGoal.getSelectedItem().toString();
        String gender = spinnerGender.getSelectedItem().toString();
        HashMap<String,String> registerInfo = new HashMap<String, String>();
        registerInfo.put("name", name);
        registerInfo.put("username", username);
        registerInfo.put("password",password);
        registerInfo.put("email",email);
        registerInfo.put("age",age);
        registerInfo.put("weight",weight);
        registerInfo.put("goal",goal);
        registerInfo.put("gender",gender);

        userNetworker.checkIfValid(registerInfo);

        // Check if username is available
        // Enter user into database


    }
    public void checkSignUpSuccess(String response){

        Boolean check = Boolean.valueOf(response);
        if(check) {

            String username = textUsername.getText().toString();
            session = new SessionManager(getApplicationContext());

            System.out.println("nýr user "+username);

            session.createLoginSession(username);

            Toast.makeText(getApplicationContext(), "Redirecting...", Toast.LENGTH_SHORT).show();

            // Redirect user to Homepage.
            Intent intent = new Intent(SignUpActivity.this, HomePageActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), "Error on Sign up", Toast.LENGTH_SHORT).show();
        }

    }
}
