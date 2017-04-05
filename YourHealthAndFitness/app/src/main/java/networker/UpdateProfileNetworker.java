package networker;

import android.app.Activity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import comunicator.AppController;

public class UpdateProfileNetworker {
    private static String TAG = UpdateProfileNetworker.class.getSimpleName();
    //private TextView txtResponse;

    public interface setupdateMyProfileTextCallBack{
        void setupdateProfileText(JSONArray userArray);
    }
    private setupdateMyProfileTextCallBack updateProfileActivity;

    public UpdateProfileNetworker(Activity activity){
        updateProfileActivity = (setupdateMyProfileTextCallBack)activity;
    }


    public void getUserProfileInfo(String username) {

        JsonArrayRequest req = new JsonArrayRequest("http://192.168.122.1:8080/mobile_myProfile?username="+username,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG+"------------", response.toString());

                        updateProfileActivity.setupdateProfileText(response);
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


    public void updateUser(HashMap<String,String> UpdateInfo){

        JSONObject jsonBody = new JSONObject(UpdateInfo);
        final String requestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.122.1:8080/mobile_updateUser", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);

                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

}
