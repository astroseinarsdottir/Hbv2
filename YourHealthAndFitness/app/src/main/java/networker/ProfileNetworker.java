package networker;

import android.app.Activity;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import comunicator.AppController;

public class ProfileNetworker {
    private static String TAG = UserNetworker.class.getSimpleName();
    //private TextView txtResponse;

    public interface setMyPrifileTextCallBack{
        void setProfileText(JSONArray userArray);
    }
    private ProfileNetworker.setMyPrifileTextCallBack profileActivity;

    public ProfileNetworker(Activity activity){
        profileActivity = (ProfileNetworker.setMyPrifileTextCallBack)activity;
    }


    public void getUserProfileInfo(String username) {

        JsonArrayRequest req = new JsonArrayRequest("192.168.122.1:8080/mobile_myProfile?username="+username,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG+"------------", response.toString());

                        profileActivity.setProfileText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //Toast.makeText(getApplicationContext(),
                //      error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

}
