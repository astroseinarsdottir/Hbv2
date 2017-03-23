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
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DietJournalActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_journal);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);

        expandableListView = (ExpandableListView)findViewById(R.id.exListView_Diet);

        // Init our list view
        initListData();
        listAdapter = new ExpandableListAdapter(this,listDataHeader,listHashMap);
        expandableListView.setAdapter(listAdapter);

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
                        intent = new Intent(DietJournalActivity.this, IndexActivity.class);
                        startActivity(intent);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                }
                return true;

            }
        });
    }

    // Put our data into the view.
    private void initListData() {
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

        // Create item belongin to header.
        List<String> monday = new ArrayList<>();
        monday.add("mánudagsmatur");
        monday.add("prufa2");
        monday.add("prufffffa");

        List<String> tuesday = new ArrayList<>();
        tuesday.add("þriðjudagsmatur");

        List<String> wednesday = new ArrayList<>();
        wednesday.add("miðvikudagsmatur");

        List<String> thurday = new ArrayList<>();
        thurday.add("fimmtudagsmatur");

        List<String> friday = new ArrayList<>();
        friday.add("mánudagsmatur");

        List<String> saturday = new ArrayList<>();
        saturday.add("laugardagsmatur");

        List<String> sunday = new ArrayList<>();
        sunday.add("sunnudagsmatur");
        //for each food in day, add to day.+
        // T.d sunday.add(typeOfMeal+ ":" + name)

        listHashMap.put(listDataHeader.get(0), monday);
        listHashMap.put(listDataHeader.get(1), tuesday);
        listHashMap.put(listDataHeader.get(2), wednesday);
        listHashMap.put(listDataHeader.get(3), thurday);
        listHashMap.put(listDataHeader.get(4), friday);
        listHashMap.put(listDataHeader.get(5), saturday);
        listHashMap.put(listDataHeader.get(6), sunday);


    }

    // For the navigation menu.
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();
    }
}
