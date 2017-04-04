package networker;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.astrosei.yourhealthandfitness.ProgressActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import comunicator.AppController;

/**
 * Created by viktor on 04/04/17.
 */

public class StatsNetworker extends AppCompatActivity {

    private static String TAG = StatsNetworker.class.getSimpleName();

    public interface statsCallback{
        void getStats(HashMap<String,String> response);
    }

    private statsCallback progressActivity;
    public StatsNetworker(Activity activity){
        progressActivity = (statsCallback) activity;
    }

    public void getStatistics(String username, String goal) {
        int id = 1;
        JsonArrayRequest req = new JsonArrayRequest("http://130.208.151.221:8080/mobile_stats?username="+username+"&id="+id+"&goal="+goal,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        HashMap<String,String> stats = new HashMap<String, String>();
                        for (int i = 0; i < response.length(); i++) {

                            JSONObject jobject = null;
                            try {
                                jobject = (JSONObject) response.get(i);
                                String date = jobject.getString("date");
                                String average = jobject.getString("average");
                                stats.put(date,average);

                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        progressActivity.getStats(stats);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //Toast.makeText(getApplicationContext(),
                // error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }
}
