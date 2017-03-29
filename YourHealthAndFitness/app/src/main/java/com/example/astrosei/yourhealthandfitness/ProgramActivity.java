package com.example.astrosei.yourhealthandfitness;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class ProgramActivity extends AppCompatActivity {

    // For navigation toolbar
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private TextView textView;

    private TableLayout tableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        tableLayout = (TableLayout)findViewById(R.id.tableLayout);

        // Date of the program
        String date = getIntent().getStringExtra("exerciseDate");

        // Get program
        initTableData();


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
                        intent = new Intent(ProgramActivity.this, IndexActivity.class);
                        startActivity(intent);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                }
                return true;

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
    public void initTableData() {

        TableRow header = new TableRow(this);

        TextView name = new TextView(this);
        name.setText("Name");
        name.setTextSize(20);
        name.setPadding(20,10,20,10);
        header.addView(name);

        TextView set = new TextView(this);
        set.setText("Set");
        set.setTextSize(20);
        set.setPadding(20,10,20,10);
        header.addView(set);

        TextView reps = new TextView(this);
        reps.setText("Reps");
        reps.setTextSize(20);
        reps.setPadding(20,10,20,10);
        header.addView(reps);

        TextView weights  = new TextView(this);
        weights.setText("Weight in kg");
        weights.setTextSize(20);
        weights.setPadding(20,10,20,10);
        header.addView(weights);

        tableLayout.addView(header);

        for (int i = 0; i < 4; i++) {

            // Set the name of an exercise alone in a row.
            TableRow exerciseName = new TableRow(this);
            TextView nameText = new TextView(this);
            nameText.setText("Exercise name");
            nameText.setPadding(20,10,20,10);
            exerciseName.addView(nameText);
            tableLayout.addView(exerciseName);

            // Add sets and reps.
            for(int j = 0; j < 4; j++){

                TableRow exercise = new TableRow(this);
                TextView empty = new TextView(this);
                empty.setGravity(Gravity.CENTER);
                empty.setText("");
                empty.setPadding(0,0,20,0);
                exercise.addView(empty);

                TextView setNr = new TextView(this);
                setNr.setGravity(Gravity.CENTER);
                setNr.setText("set "+i);

                exercise.addView(setNr);

                TextView rep = new TextView(this);
                rep.setGravity(Gravity.CENTER);
                rep.setText("rep " +i);
                exercise.addView(rep);

                TextView weight = new TextView(this);
                weight.setGravity(Gravity.CENTER);
                weight.setText("weight "+ i);
                exercise.addView(weight);

                tableLayout.addView(exercise);

            }

        }
    }
}
