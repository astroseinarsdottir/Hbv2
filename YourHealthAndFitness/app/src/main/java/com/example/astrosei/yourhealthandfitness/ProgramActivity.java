package com.example.astrosei.yourhealthandfitness;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.icu.text.DisplayContext;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import networker.ProgramNetworker;
import sessions.SessionManager;

public class ProgramActivity extends AppCompatActivity implements ProgramNetworker.getCurrentCycleCallBackForProgram{

    // For navigation toolbar
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private TextView textView;
    private Button btn_Result;

    ProgramNetworker programNetworker = new ProgramNetworker(this);
    // For program table
    private TableLayout tableLayout;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        tableLayout = (TableLayout)findViewById(R.id.tableLayout);
        btn_Result = (Button)findViewById(R.id.btn_Result);

        // Date of the program
        final String date = getIntent().getStringExtra("exerciseDate");
        Log.i(date, "---------------------");
        if(date.equalsIgnoreCase("24/11/2016")){
            Log.i(date, "---------------------+++++++++++++++++++++++++++");
        }

        // Get program
        session = new SessionManager(getApplicationContext());
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        // name
        String username = user.get(SessionManager.KEY_NAME);
        programNetworker.getCurrentCycleRequest(username, date);

        // Set our special toolbar as the action bar.
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Program");


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
                        intent = new Intent(ProgramActivity.this, HomePageActivity.class);
                        startActivity(intent);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.logout_id:
                        // Logout user, not fully implemented.
                        session = new SessionManager(getApplicationContext());
                        session.logoutUser();
                        intent = new Intent(ProgramActivity.this, IndexActivity.class);
                        startActivity(intent);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                }
                return true;

            }
        });

        // Redirects user to update result activity.
        btn_Result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProgramActivity.this, UpdateExerciseOfTodayActivity.class);
                intent.putExtra("exerciseDate",date);
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

    // Inputs data into table
    public void initTableData(JSONArray currCycle, String date) {

        // Create header row.
        TableRow header = new TableRow(this);

        TextView name = new TextView(this);
        name.setText("Name");
        name.setTextSize(20);
        name.setTypeface(null, Typeface.BOLD);
        name.setPadding(20,10,20,10);
        header.addView(name);

        TextView set = new TextView(this);
        set.setText("Set");
        set.setTextSize(20);
        set.setTypeface(null, Typeface.BOLD);
        set.setPadding(20,10,20,10);
        header.addView(set);

        TextView reps = new TextView(this);
        reps.setText("Reps");
        reps.setTextSize(20);
        reps.setTypeface(null, Typeface.BOLD);
        reps.setPadding(20,10,20,10);
        header.addView(reps);

        TextView weights  = new TextView(this);
        weights.setText("Last Weight used");
        weights.setTextSize(20);
        weights.setTypeface(null,Typeface.BOLD);
        weights.setPadding(20,10,20,10);
        header.addView(weights);

        tableLayout.addView(header);
/*
        TableRow exerciseName = new TableRow(this);
        TextView nameText = new TextView(this);

        nameText.setText("utan if IF");

        nameText.setPadding(20,10,20,10);
        nameText.setTextSize(14);
        exerciseName.addView(nameText);
        tableLayout.addView(exerciseName);
*/



        for (int i = 0; i < currCycle.length(); i++) {

            // Set the name of an exercise alone row.

            try{

                //TableRow exerciseName = new TableRow(this);
                //TextView nameText = new TextView(this);

                JSONObject dayObject = (JSONObject) currCycle.get(i);

                if(dayObject.getString("date").equalsIgnoreCase(date)) {
                    Log.i(dayObject.getString("date"), date + "--------------------------------");
                    JSONArray exercisesArray = dayObject.getJSONArray("exercises");
                    Log.i(exercisesArray.toString(),"0000000000000000000");

                    //JSONObject exerciseOBJECTTEST = (JSONObject) exercisesArray.get(0);


                    for(int u = 0; u< exercisesArray.length(); u++){
                        JSONObject exerciseOBJECT = (JSONObject) exercisesArray.get(u);

                        String exerciseNNAMEBITS = exerciseOBJECT.getString("name");


                        TableRow exerciseName = new TableRow(this);
                        TextView nameText = new TextView(this);

                        nameText.setText(exerciseNNAMEBITS);

                        nameText.setPadding(20,10,20,10);
                        nameText.setTextSize(14);
                        exerciseName.addView(nameText);
                        tableLayout.addView(exerciseName);

                        JSONArray setArray = exerciseOBJECT.getJSONArray("set");

                        // Add sets, reps and suggested weight.
                        for(int j = 0; j < setArray.length(); j++) {

                            TableRow exercise = new TableRow(this);

                            TextView empty = new TextView(this);
                            empty.setGravity(Gravity.CENTER);
                            empty.setText("");
                            empty.setPadding(20, 5, 20, 5);
                            empty.setTextSize(14);
                            exercise.addView(empty);

                            JSONObject setOBJECT = (JSONObject) setArray.get(j);

                            String setNumber = setOBJECT.getString("number");

                            TextView setNr = new TextView(this);
                            setNr.setGravity(Gravity.CENTER);
                            setNr.setText("set " + setNumber);
                            setNr.setPadding(20, 5, 20, 5);
                            setNr.setTextSize(14);
                            exercise.addView(setNr);

                            String repNumber = setOBJECT.getString("rep");

                            TextView rep = new TextView(this);
                            rep.setGravity(Gravity.CENTER);
                            rep.setText("rep " + repNumber);
                            rep.setPadding(20, 5, 20, 5);
                            rep.setTextSize(14);
                            exercise.addView(rep);

                            String weightNumber = setOBJECT.getString("weight");

                            TextView weight = new TextView(this);
                            weight.setGravity(Gravity.CENTER);
                            weight.setText("weight " + weightNumber);
                            weight.setPadding(20, 5, 20, 5);
                            weight.setTextSize(14);
                            exercise.addView(weight);

                            tableLayout.addView(exercise);
                        }

                    }

                }

            }
            catch(JSONException e){
                e.printStackTrace();
            }

        }
    }
}
