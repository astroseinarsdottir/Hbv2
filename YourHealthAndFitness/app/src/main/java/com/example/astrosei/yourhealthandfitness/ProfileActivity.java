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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    private TextView textView_Name;
    private TextView textView_Email;
    private TextView textView_Age;
    private TextView textView_Goal;
    private TextView textView_Gender;
    private TextView textView_Weight;
    private Button  btn_Edit;

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textView_Name = (TextView)findViewById(R.id.textView_Name);
        textView_Email = (TextView)findViewById(R.id.textView_Email);
        textView_Age = (TextView)findViewById(R.id.textView_Age);
        textView_Goal = (TextView)findViewById(R.id.textView_Goal);
        textView_Gender = (TextView)findViewById(R.id.textView_Gender);
        textView_Weight = (TextView)findViewById(R.id.textView_Weight);
        btn_Edit = (Button)findViewById(R.id.btn_Edit);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);

        // Set the right values into the view
        // Not implemented, take values from database.
        textView_Name.setText("Ástrós Einarsdóttir");
        textView_Email.setText("Prufa1");
        textView_Age.setText("Prufa2");
        textView_Goal.setText("Prufa3");
        textView_Gender.setText("Prufa4");
        textView_Weight.setText("Prufa5");

        // User wants to edit his profile, redirected to Update profile page.
        btn_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, UpdateProfileActivity.class);
                startActivity(intent);
            }
        });

        // Set our special toolbar as the action bar.
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");

        // Creating the hamburger menu.
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.drawer_open,R.string.drawer_close );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        // Handler for navigation in the toolbar.
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.home_id:
                        // Return to home page
                        intent = new Intent(ProfileActivity.this, HomePageActivity.class);
                        startActivity(intent);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.logout_id:
                        // Logout user, not fully implemented.
                        intent = new Intent(ProfileActivity.this, IndexActivity.class);
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
}
