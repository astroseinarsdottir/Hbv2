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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

import networker.WorkoutNetworker;
import sessions.SessionManager;

public class ExercisesActivity extends AppCompatActivity implements WorkoutNetworker.getCurrentCycleCallback{

    // For navigation toolbar
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ListView listView;

    WorkoutNetworker workoutNetworker = new WorkoutNetworker(this);
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        listView = (ListView)findViewById(R.id.listView);

        session = new SessionManager(getApplicationContext());
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        // name
        String username = user.get(SessionManager.KEY_NAME);

        workoutNetworker.getCurrentCycleRequest(username);
        /*
        // Set our listView
        String[] dates = {"22/1/2017","23/1/2017","24/1/2017"};
        String[] names = {"Legs", "Hands", "Core"};
        CustomListAdapter customListAdapter = new CustomListAdapter(dates, names);
        listView.setAdapter(customListAdapter);
        */
        // Set our special toolbar as the action bar.
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Exercises");


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
                        intent = new Intent(ExercisesActivity.this, HomePageActivity.class);
                        startActivity(intent);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.logout_id:
                        // Logout user, not fully implemented.
                        intent = new Intent(ExercisesActivity.this, IndexActivity.class);
                        startActivity(intent);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                }
                return true;

            }
        });

        // Handler for listView, redirect the user to the right program.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ExercisesActivity.this, ProgramActivity.class);
                Object objDate;
                String stringDate= "";
                // Redirects user to program for the right day chosen.
                switch (position){
                    case 0:
                        objDate = listView.getItemAtPosition(position);
                        stringDate = objDate.toString();

                        break;
                    case 1:
                        objDate = listView.getItemAtPosition(position);
                        stringDate = objDate.toString();

                        break;
                    case 2:
                        objDate = listView.getItemAtPosition(position);
                        stringDate = objDate.toString();


                        break;
                    case 3:
                        objDate = listView.getItemAtPosition(position);
                        stringDate = objDate.toString();

                        break;
                    case 4:
                        objDate = listView.getItemAtPosition(position);
                        stringDate = objDate.toString();

                        break;
                }
                intent.putExtra("exerciseDate", stringDate);
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

    // Adapter for our listView
    class CustomListAdapter extends BaseAdapter{

        private String[] exerciseDates;
        private String[] exerciseNames;

        public CustomListAdapter(String[] exerciseDates, String[] exerciseNames){

            this.exerciseDates = exerciseDates;
            this.exerciseNames = exerciseNames;
        }
        @Override
        public int getCount() {
            return exerciseDates.length;
        }

        @Override
        public Object getItem(int position) {
            return exerciseDates[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = getLayoutInflater().inflate(R.layout.exercises_row_layout,null);

            TextView date = (TextView)convertView.findViewById(R.id.textView_Date);
            TextView name = (TextView)convertView.findViewById(R.id.textView_Name);

            date.setText(exerciseDates[position]);
            name.setText(exerciseNames[position]);

            return convertView;
        }
    }

    public void getCurrentCycle(JSONArray currCycle){
        try{
            JSONObject legDayObject = (JSONObject) currCycle.get(4);
            JSONObject chestDayObject = (JSONObject) currCycle.get(3);
            JSONObject backDayObject = (JSONObject) currCycle.get(2);
            JSONObject shoulderDayObject = (JSONObject) currCycle.get(1);
            JSONObject handsDayObject = (JSONObject) currCycle.get(0);
            Log.i("------------------",currCycle.get(0).toString());
            String legDayDate = legDayObject.getString("date");
            String chestDayDate = chestDayObject.getString("date");
            String backDayDate = backDayObject.getString("date");
            String shoulderDayDate = shoulderDayObject.getString("date");
            String handsDayDate = handsDayObject.getString("date");


            //String[] dates = {"22/1/2017","23/1/2017","24/1/2017"};
            //String[] names = {"Legs", "Hands", "Core"};

            String[] dates = {legDayDate, chestDayDate, backDayDate, shoulderDayDate, handsDayDate};
            String[] names = {"Legs", "Chest", "Back", "Shoulders", "Hands"};

            CustomListAdapter customListAdapter = new CustomListAdapter(dates, names);
            listView.setAdapter(customListAdapter);


        }
        catch (JSONException e){
            e.printStackTrace();
        }

    }

}
