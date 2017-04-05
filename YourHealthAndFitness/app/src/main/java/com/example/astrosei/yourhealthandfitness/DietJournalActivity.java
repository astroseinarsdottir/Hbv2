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
import android.widget.ExpandableListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import networker.FoodNetworker;
import networker.UserNetworker;
import sessions.SessionManager;

public class DietJournalActivity extends AppCompatActivity implements FoodNetworker.getDietPlanCallback{

    // For navigation toolbar
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    // For expandable list view
    private ExpandableListView expandableListView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHashMap;

    private Button btn_Log;
    SessionManager session;

    FoodNetworker foodNetworker = new FoodNetworker(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_journal);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);

        expandableListView = (ExpandableListView)findViewById(R.id.exListView_Diet);

        btn_Log = (Button)findViewById(R.id.btn_Log);



        // User wants to log his diet.
        btn_Log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DietJournalActivity.this, LogJournalActivity.class);
                startActivity(intent);
            }
        });

        // Init our list view
        //initListData();
        session = new SessionManager(getApplicationContext());
        // get user data from session
        HashMap<String, String> user = session.getUserDetails();
        // name
        String name = user.get(SessionManager.KEY_NAME);

        foodNetworker.getDietPlan(name);


        // Make so that only one list can be open at once.
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            // Keep track of previous expanded parent
            int previousGroup = -1;
            @Override
            public void onGroupExpand(int groupPosition) {
                // Collapse previous parent if expanded.
                if((previousGroup!=-1)&&(groupPosition!=previousGroup)){
                    expandableListView.collapseGroup(previousGroup);
                }
                previousGroup = groupPosition;
            }
        });

        // Set our special toolbar as the action bar.
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Diet Journal");


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
                        // Return to home page
                        intent = new Intent(DietJournalActivity.this, HomePageActivity.class);
                        startActivity(intent);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.logout_id:
                        // Logout user, not fully implemented.
                        session = new SessionManager(getApplicationContext());
                        session.logoutUser();
                        //intent = new Intent(DietJournalActivity.this, IndexActivity.class);
                        //startActivity(intent);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                }
                return true;

            }
        });
    }

    // Put our data into the view.
    public void initListData(JSONArray foodArray) {


        listDataHeader = new ArrayList<>();
        listHashMap = new HashMap<>();

        // Create headers.
        listDataHeader.add("Monday");
        listDataHeader.add("Tuesday");
        listDataHeader.add("Wednesday");
        listDataHeader.add("Thursday");
        listDataHeader.add("Friday");
        listDataHeader.add("Saturday");
        listDataHeader.add("Sunday");
        try {

            JSONObject food_0 = (JSONObject) foodArray.get(0);
            JSONObject food_1 = (JSONObject) foodArray.get(1);
            JSONObject food_2 = (JSONObject) foodArray.get(2);
            // Create item belongin to header.
            List<String> monday = new ArrayList<>();

            monday.add(food_0.getString("name"));
            monday.add(food_0.getString("typeOfMeal"));
            monday.add(food_1.getString("name"));
            monday.add(food_1.getString("typeOfMeal"));
            monday.add(food_2.getString("name"));
            monday.add(food_2.getString("typeOfMeal"));

            List<String> tuesday = new ArrayList<>();
            tuesday.add(food_0.getString("name"));
            tuesday.add(food_0.getString("typeOfMeal"));
            tuesday.add(food_1.getString("name"));
            tuesday.add(food_1.getString("typeOfMeal"));
            tuesday.add(food_2.getString("name"));
            tuesday.add(food_2.getString("typeOfMeal"));

            List<String> wednesday = new ArrayList<>();
            wednesday.add(food_0.getString("name"));
            wednesday.add(food_0.getString("typeOfMeal"));
            wednesday.add(food_1.getString("name"));
            wednesday.add(food_1.getString("typeOfMeal"));
            wednesday.add(food_2.getString("name"));
            wednesday.add(food_2.getString("typeOfMeal"));

            List<String> thurday = new ArrayList<>();
            thurday.add(food_0.getString("name"));
            thurday.add(food_0.getString("typeOfMeal"));
            thurday.add(food_1.getString("name"));
            thurday.add(food_1.getString("typeOfMeal"));
            thurday.add(food_2.getString("name"));
            thurday.add(food_2.getString("typeOfMeal"));

            List<String> friday = new ArrayList<>();
            friday.add(food_0.getString("name"));
            friday.add(food_0.getString("typeOfMeal"));
            friday.add(food_1.getString("name"));
            friday.add(food_1.getString("typeOfMeal"));
            friday.add(food_2.getString("name"));
            friday.add(food_2.getString("typeOfMeal"));

            List<String> saturday = new ArrayList<>();
            saturday.add(food_0.getString("name"));
            saturday.add(food_0.getString("typeOfMeal"));
            saturday.add(food_1.getString("name"));
            saturday.add(food_1.getString("typeOfMeal"));
            saturday.add(food_2.getString("name"));
            saturday.add(food_2.getString("typeOfMeal"));

            List<String> sunday = new ArrayList<>();
            sunday.add(food_0.getString("name"));
            sunday.add(food_0.getString("typeOfMeal"));
            sunday.add(food_1.getString("name"));
            sunday.add(food_1.getString("typeOfMeal"));
            sunday.add(food_2.getString("name"));
            sunday.add(food_2.getString("typeOfMeal"));
            //for each food in day, add to day.+
            // T.d sunday.add(typeOfMeal+ ":" + name)

            listHashMap.put(listDataHeader.get(0), monday);
            listHashMap.put(listDataHeader.get(1), tuesday);
            listHashMap.put(listDataHeader.get(2), wednesday);
            listHashMap.put(listDataHeader.get(3), thurday);
            listHashMap.put(listDataHeader.get(4), friday);
            listHashMap.put(listDataHeader.get(5), saturday);
            listHashMap.put(listDataHeader.get(6), sunday);

            listAdapter = new ExpandableListAdapter(this,listDataHeader,listHashMap);
            expandableListView.setAdapter(listAdapter);

        }
        catch (Exception e){
            Log.i("errer","error i dietjournal");
        }


    }

    // For the navigation menu.
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();
    }
}
