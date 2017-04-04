package networker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import comunicator.AppController;



public class WorkoutNetworker {
    private static String TAG = WorkoutNetworker.class.getSimpleName();
    private TextView txtResponse;
    private SimpleDateFormat SimpleDate = new SimpleDateFormat("dd/mm/yyyy");

    // temporary string to show the parsed response
    private String jsonResponse;
    private ProgressDialog pDialog;


    public interface getCurrentCycleCallback{
        void getCurrentCycle(JSONArray currCycle);
    }
    private WorkoutNetworker.getCurrentCycleCallback exercisesActivity;

    public WorkoutNetworker(Activity activity){

        exercisesActivity = (WorkoutNetworker.getCurrentCycleCallback)activity;
    }


    public void getCurrentCycleRequest(String username) {

        JsonArrayRequest req = new JsonArrayRequest("http://130.208.151.221:8080/mobile_currentCycle?username="+username,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        JSONArray cycleArray = response;
                        exercisesActivity.getCurrentCycle(cycleArray);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    private String URLworkout = "http://130.208.151.228:8181/mobile_workoutOfToday";



}
