package com.example.astrosei.yourhealthandfitness;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class UpdateProfileActivity extends AppCompatActivity {

    // For navigation toolbar
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    // Content
    private Spinner spinnerGoal;
    private EditText textAge;
    private EditText textWeight;
    private TextView textView_Info;
    private Button btn_EditProfile;

    private ArrayAdapter<String> goalAdapter;
    private String[] goals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);

        spinnerGoal = (Spinner)findViewById(R.id.spinner_Goal);
        textAge = (EditText)findViewById(R.id.text_Age);
        textWeight = (EditText)findViewById(R.id.text_Weight);
        btn_EditProfile = (Button)findViewById(R.id.btn_EditProfile);
        textView_Info = (TextView)findViewById(R.id.textView_Info);

        goals = new String[]{"Stronger","Buffer", "Leaner"};

        // Create an ArrayAdapter and add the list of goals to it.
        goalAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, goals);

        // Set title for the spinner in the UI.
        spinnerGoal.setPrompt("Choose your goal");

        // Apply the adapter to the spinner so the user can view the list of goals.
        spinnerGoal.setAdapter(goalAdapter);

        // Set layout so that the dropdown is not so dense.
        goalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set our users current info into view.
        // Not fully implemented, need to get info from database.
        textAge.setText("22");
        textWeight.setText("55.5");
        spinnerGoal.setSelection(1);

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

        // Set the info text.
        // Not fully implemented, need to get nextUpdate from database.
        textView_Info.setText("If the goal is changed then you will get a new program on DATE");

        // Set our special toolbar as the action bar.
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit profile");


        // Creating the hamburger menu.
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.drawer_open,R.string.drawer_close );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        // Handler for navigation in the toolbar.
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.home_id:
                        // Return to home pageq
                        intent = new Intent(UpdateProfileActivity.this, HomePageActivity.class);
                        startActivity(intent);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.logout_id:
                        // Logout user, not fully implemented.
                        intent = new Intent(UpdateProfileActivity.this, IndexActivity.class);
                        startActivity(intent);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                }
                return true;

            }
        });

        // User wants to update information. Redirected to profile when done
        btn_EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateProfile();
                // Redirect
                Intent intent = new Intent(UpdateProfileActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
    // For the navigation menu.
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();
    }

    // Get info from view and put it into database
    public void updateProfile(){

        String age = textAge.getText().toString();
        String weight = textWeight.getText().toString();
        String goal = spinnerGoal.getSelectedItem().toString();

    }
}
