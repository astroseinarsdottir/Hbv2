package com.example.astrosei.yourhealthandfitness;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class HomePageActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ListView listView;

    private ActionBarDrawerToggle actionBarDrawerToggle;

    int[] Images = {R.drawable.exercises,R.drawable.update_result,R.drawable.diet,
            R.drawable.progress,R.drawable.profile};
    String[] Titles = {"Exercise Program","Update Today","Diet Journal","Progress","Profile"};
    String[] Descriptions = {"View your exercise program for the week","Insert result for today's exercise"
            ,"View your diet program for the week","View your progress","View your profile"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);
        listView = (ListView)findViewById(R.id.listView);


        // To set our special listView with images into the view.
        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);

        // Set our special toolbar as the action bar.
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home page");


        // Creating the hamburger menu.
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.drawer_open,R.string.drawer_close );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        // Handler for navigation in the toolbar.
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home_id:
                        getSupportActionBar().setTitle("Home");
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.logout_id:
                        // Logout user, not fully implemented.
                        Intent intent = new Intent(HomePageActivity.this, IndexActivity.class);
                        startActivity(intent);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;
                }
                return true;

            }
        });

        // Redirects the user to the right activity when he clicks on it in the list.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent;
                switch (position){
                    case 0:
                        intent =  new Intent(HomePageActivity.this,ExercisesActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent =  new Intent(HomePageActivity.this,UpdateExerciseOfTodayActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent =  new Intent(HomePageActivity.this,DietJournalActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent =  new Intent(HomePageActivity.this,ProgressActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent =  new Intent(HomePageActivity.this,ProfileActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    // For the navigation menu.
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();
    }


    // Special class so the listView can have images.
    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return Images.length;
        }

        @Override
        public Object getItem(int position) {
            return Titles[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            convertView = getLayoutInflater().inflate(R.layout.row_layout,null);

            ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);
            TextView textView_Title = (TextView)convertView.findViewById(R.id.textView_Title);
            TextView textView_Description = (TextView)convertView.findViewById(R.id.textView_Description);

            imageView.setImageResource(Images[position]);
            textView_Title.setText(Titles[position]);
            textView_Description.setText(Descriptions[position]);

            return convertView;
        }
    }
}
