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

import java.text.SimpleDateFormat;

import comunicator.AppController;

/**
 * Created by solvi on 4.4.2017.
 */

public class ProgramNetworker {
    private static String TAG = ProgramNetworker.class.getSimpleName();
    private TextView txtResponse;
    private SimpleDateFormat SimpleDate = new SimpleDateFormat("dd/mm/yyyy");

    // temporary string to show the parsed response
    private String jsonResponse;
    private ProgressDialog pDialog;

    public interface getCurrentCycleCallBackForProgram{
        void initTableData(JSONArray currCycle, String date);
    }

    private ProgramNetworker.getCurrentCycleCallBackForProgram programActivity;

    public ProgramNetworker(Activity activity){
        programActivity = (ProgramNetworker.getCurrentCycleCallBackForProgram)activity;

    }

    public void getCurrentCycleRequest(String username, final String date) {

        JsonArrayRequest req = new JsonArrayRequest("http://130.208.151.228:8181/mobile_currentCycle?username="+username,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        Log.i(date, "--------------------------");
                        JSONArray cycleArray = response;
                        programActivity.initTableData(cycleArray, date);

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
