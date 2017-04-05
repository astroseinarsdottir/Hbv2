package com.example.astrosei.yourhealthandfitness;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import networker.UpdateExerciseNetworker;
import sessions.SessionManager;

import static com.example.astrosei.yourhealthandfitness.R.id.tableLayout;



public class UpdateExerciseOfTodayActivity extends AppCompatActivity implements UpdateExerciseNetworker.getCurrentCycleCallBackForProgram{

    UpdateExerciseNetworker updateExerciseNetworker = new UpdateExerciseNetworker(this);
    // For navigation toolbar
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    // For program table
    private TableLayout tableLayout;
    private EditText editText;

    private Button btn_Submit;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_exercise_of_today);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        tableLayout = (TableLayout)findViewById(R.id.tableLayout);
        btn_Submit = (Button)findViewById(R.id.btn_Submit);

        // Date of the program
        String date = getIntent().getStringExtra("exerciseDate");

        // Get program
        session = new SessionManager(getApplicationContext());
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        // name
        String username = user.get(SessionManager.KEY_NAME);
        //updateExerciseNetworker.getCurrentCycleRequest(username, date);

        // Set our special toolbar as the action bar.
        setSupportActionBar(toolbar);
        if(date==null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Calendar c = Calendar.getInstance();
            Date todaysdate = c.getTime();
            String todaysdatestring = dateFormat.format(todaysdate);
            getSupportActionBar().setTitle(todaysdatestring);
            updateExerciseNetworker.getCurrentCycleRequest(username, "05/04/2017");

        }
        else{
            getSupportActionBar().setTitle(date +" results");
            updateExerciseNetworker.getCurrentCycleRequest(username, date);

        }

        //final int numberOfInputs = initTableData();

        // Not fully implemented, puts the weights into database.
        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //submit(numberOfInputs);
                Intent intent = new Intent(UpdateExerciseOfTodayActivity.this,HomePageActivity.class);
                startActivity(intent);
            }
        });

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
                        intent = new Intent(UpdateExerciseOfTodayActivity.this, HomePageActivity.class);
                        startActivity(intent);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.logout_id:
                        // Logout user, not fully implemented.
                        session = new SessionManager(getApplicationContext());
                        session.logoutUser();
                        intent = new Intent(UpdateExerciseOfTodayActivity.this, IndexActivity.class);
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

    public void initTableData(JSONArray currCycle, String date){
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
        weights.setText("Weight in kg");
        weights.setTextSize(20);
        weights.setTypeface(null,Typeface.BOLD);
        weights.setPadding(20,10,20,10);
        header.addView(weights);

        tableLayout.addView(header);

        int id = 0;
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

                            EditText weight = new EditText(this);
                            weight.setGravity(Gravity.CENTER);
                            weight.setHint("weight "+ i);
                            weight.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                            // Make easier for user to fill in result.
                            // Have to be changed when objects have been added.
                            if(i+1==4 && j+1==4){
                                weight.setImeOptions(EditorInfo.IME_ACTION_DONE);
                            }
                            else {
                                weight.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                            }
                            weight.setPadding(20,5,20,5);
                            weight.setTextSize(14);
                            weight.setId(id);
                            exercise.addView(weight);

                            tableLayout.addView(exercise);

                            // So the id for the weights are unique.
                            id++;
                        }

                    }

                }

            }
            catch(JSONException e){
                e.printStackTrace();
            }
            }

        }
        //return id;


    // Puts the weights that the user submited into database.
    public void submit(int numberOfInputs){

        // Fetch input from editText fields and add them to object.
        for(int i = 0; i< numberOfInputs; i++) {
            editText = (EditText) findViewById(i);
            String weight = editText.getText().toString();
            //Add to object, not implemented.
        }
    }
}
