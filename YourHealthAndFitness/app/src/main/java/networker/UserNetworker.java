package networker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import comunicator.AppController;
import com.example.astrosei.yourhealthandfitness.LoginActivity;

public class UserNetworker extends AppCompatActivity {
    private static String TAG = UserNetworker.class.getSimpleName();
    //private TextView txtResponse;


    String solviUrl = "http://130.208.100.213:8080/mobile_login";


    public interface loginCallback{
        void login(String response);
    }
    private loginCallback loginActivity;

    public UserNetworker(Activity activity){

        loginActivity = (loginCallback)activity;
    }
    //LoginActivity loginActivity = new LoginActivity();
    // temporary string to show the parsed response
    //private String jsonResponse;


    public void loginRequest(String username, String password) {
        StringRequest req = new StringRequest(solviUrl+"?un="+username+"&pw="+password,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);

                        try {
                            Log.i(response,"---------------");
                            //txtResponse.setText(jsonResponse);

                            loginActivity.login(response);

                        } catch (Exception e) {
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
