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

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import networker.StatsNetworker;
import sessions.SessionManager;

import static com.example.astrosei.yourhealthandfitness.R.id.graph;

public class VisualizeActivity extends AppCompatActivity implements StatsNetworker.statsCallback{

    StatsNetworker statsNetworker = new StatsNetworker(this);
    // For navigation toolbar
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    public Double max = 0.0;

    private LineGraphSeries<DataPoint> series;//Tengt vizutest
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualize);
        statsNetworker.getStatistics("sol","stronger");
 /*       //Vizutest
        double[] x = new double[] {1,2,3,4,5};
        double[] y = new double[]{7.823,13.411,19.294,7.529,5.67};

        GraphView graphView = (GraphView) findViewById(R.id.graph);

        DataPoint[] dataPoints = new DataPoint[x.length];
        for(int i = 0; i < x.length; i++){
            dataPoints[i] = new DataPoint(x[i],y[i]);
        }

       series = new LineGraphSeries<DataPoint>(dataPoints);

        graphView.getViewport().setScrollable(true);
        graphView.addSeries(series);
*/

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        navigationView = (NavigationView)findViewById(R.id.navigation_view);

        // Set our special toolbar as the action bar.
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Visualize");


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
                        intent = new Intent(VisualizeActivity.this, HomePageActivity.class);
                        startActivity(intent);
                        item.setChecked(true);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.logout_id:
                        // Logout user, not fully implemented.
                        session = new SessionManager(getApplicationContext());
                        session.logoutUser();
                        intent = new Intent(VisualizeActivity.this, IndexActivity.class);
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

    public void getStats(JSONArray statsArray) {
        //double[] y = new double[]{};
        ArrayList<Double> yaxis = new ArrayList<>();
        ArrayList<Integer> xaxis = new ArrayList<>();
        ArrayList<Date> dates = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        GraphView graphView = (GraphView) findViewById(graph);


        for(int i = 0; i<statsArray.length(); i++){
            try{
                JSONObject statsObject = (JSONObject) statsArray.get(i);
                String date = statsObject.getString("date");
                String average = statsObject.getString("average");
                Double ave = Double.parseDouble(average);
                yaxis.add(ave);
                xaxis.add(i);

                if(ave>=max){max=ave;}
                try{
                    dates.add(df.parse(date));
                }catch (ParseException e){
                    e.printStackTrace();
                }

            }
            catch (JSONException e){
                e.printStackTrace();
            }

        }
        Log.i(yaxis.toString(),"dfsdfsdfsdfsdfsdf");
        Log.i(xaxis.toString(),"sdfsdfsdfsdfsdf");
        double[] x = new double[] {0,1,2,3};
        //double[] y = new double[]{7.823,13.411,19.294,7.529,5.67};

        //GraphView graphView = (GraphView) findViewById(graph);

        DataPoint[] dataPoints = new DataPoint[xaxis.size()];
        for(int i = 0; i < xaxis.size(); i++){
            dataPoints[i] = new DataPoint(xaxis.get(i),yaxis.get(i));
        }

        series = new LineGraphSeries<DataPoint>(dataPoints);

        graphView.getViewport().setScrollable(true);
        graphView.addSeries(series);


/*
        graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getApplicationContext()));
        graphView.getGridLabelRenderer().setNumHorizontalLabels(dates.size()+1); // only 4 because of the space

        graphView.getGridLabelRenderer().setHumanRounding(false);
*/
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(0);
        nf.setMinimumIntegerDigits(1);

        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(nf, nf));
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(xaxis.size()+1);
        graphView.getViewport().setMinY(0.0);
        graphView.getViewport().setMaxY(max+1);

        //graphView.getViewport().setYAxisBoundsManual(true);
        //graphView.getViewport().setXAxisBoundsManual(true);




    }
}
