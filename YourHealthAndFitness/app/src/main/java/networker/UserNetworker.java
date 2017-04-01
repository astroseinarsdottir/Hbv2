package networker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v4.content.pm.ActivityInfoCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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


    String solviUrl = "http://192.168.1.9:8080/mobile_login";


    public interface loginCallback{
        void login(String response);
    }

    public interface setMyPrifileTextCallBack{
        void setProfileText(JSONArray userArray);
    }

    private loginCallback loginActivity;

    private  setMyPrifileTextCallBack profileActivity;

    public UserNetworker(Activity activity){

        String name =activity.getClass().getSimpleName();
        if(name.equals("LoginActivity")) {
            loginActivity = (loginCallback) activity;
            Log.d("ER í login",name);
        }
        else if(name.equals("ProfileActivity")) {
            profileActivity = (setMyPrifileTextCallBack) activity;
            Log.d("er í profile",name);
        }
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


    public void getUserProfileInfo(String username) {

        JsonArrayRequest req = new JsonArrayRequest("http://192.168.1.9:8080/mobile_myProfile?username="+username,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        profileActivity.setProfileText(response);

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
