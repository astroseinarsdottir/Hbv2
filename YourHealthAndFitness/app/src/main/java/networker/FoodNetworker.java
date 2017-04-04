package networker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import comunicator.AppController;
import sessions.SessionManager;

import static android.app.PendingIntent.getActivity;

/**
 * Created by viktor on 24/03/17.
 */

public class FoodNetworker {

    private static String TAG = FoodNetworker.class.getSimpleName();
    private TextView txtResponse;


    public interface getDietPlanCallback{
        void initListData(JSONArray food);
    }
    private FoodNetworker.getDietPlanCallback dietJournalActivity;

    public FoodNetworker(Activity activity){

        dietJournalActivity = (FoodNetworker.getDietPlanCallback)activity;
    }


    public void getDietPlan(String name) {


        JsonArrayRequest req = new JsonArrayRequest("http://10.178.240.137:8080/mobile_foodPlan?username="+name,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object
                            JSONArray foodArray = (JSONArray) response;
                            dietJournalActivity.initListData(foodArray);

                            for (int i = 0; i < response.length(); i++) {

                                JSONObject food = (JSONObject) response.get(i);

                                //dietJournalActivity.initListData(food);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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

}
