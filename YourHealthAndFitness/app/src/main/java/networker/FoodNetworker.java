package networker;

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

import comunicator.AppController;

import static android.app.PendingIntent.getActivity;

/**
 * Created by viktor on 24/03/17.
 */

public class FoodNetworker {

    private static String TAG = FoodNetworker.class.getSimpleName();
    private TextView txtResponse;

    // temporary string to show the parsed response
    private String jsonResponse;
    private ProgressDialog pDialog;

    public void makeStatsRequest(String URL) {
        showpDialog();

        JsonArrayRequest req = new JsonArrayRequest(URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object
                            jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject stats = (JSONObject) response
                                        .get(i);

                                String date = stats.getString("date");
                                String average = stats.getString("average");
                                jsonResponse += "Date: " + date + "\n\n";
                                jsonResponse += "Average: " + average + "\n\n";


                            }

                            txtResponse.setText(jsonResponse);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
