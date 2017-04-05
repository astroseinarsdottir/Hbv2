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
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

import networker.StatsNetworker;
import sessions.SessionManager;

public class ProgressActivity extends AppCompatActivity implements StatsNetworker.statsCallback{

    // For navigation toolbar
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Button vizutest;
    StatsNetworker statsNetworker = new StatsNetworker(this);
    SessionManager session;
    private TextView textView;
    private String JSONRESOPNSE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("sol","sol");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        textView = (TextView) findViewById(R.id.fackmeid);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        vizutest = (Button)findViewById(R.id.vizutest);
        String username = "sol";
        String goal = "stronger";
        //statsNetworker.getStatistics(username,goal);
        statsNetworker.getStatistics("sol","stronger");
        // Set our special toolbar as the action bar.
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Progress");


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
                        intent = new Intent(ProgressActivity.this, HomePageActivity.class);
                        startActivity(intent);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.logout_id:
                        // Logout user, not fully implemented.
                        session = new SessionManager(getApplicationContext());
                        session.logoutUser();
                        intent = new Intent(ProgressActivity.this, IndexActivity.class);
                        startActivity(intent);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                }
                return true;

            }
        });
        //Vizutest
        vizutest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(ProgressActivity.this, VisualizeActivity.class);
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

    public void getStats(JSONArray statsArray) {
        JSONRESOPNSE = "";
        for(int i = 0; i<statsArray.length(); i++){
            try{
                JSONObject statsObject = (JSONObject) statsArray.get(i);
                String date = statsObject.getString("date");
                String average = statsObject.getString("average");

                JSONRESOPNSE += "Date : " + date + "    Average : " + average + "\n\n";

            }
            catch (JSONException e){
                e.printStackTrace();
            }

        }

        textView.setText(JSONRESOPNSE);

    }

}
