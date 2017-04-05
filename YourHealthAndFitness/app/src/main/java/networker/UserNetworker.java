package networker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v4.content.pm.ActivityInfoCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.AccessController;
import java.util.HashMap;

import comunicator.AppController;
import com.example.astrosei.yourhealthandfitness.LoginActivity;

import static java.security.AccessController.getContext;

public class UserNetworker extends AppCompatActivity {
    private static String TAG = UserNetworker.class.getSimpleName();
    //private TextView txtResponse;


    String solviUrl = "http://130.208.151.228:8181//mobile_login";  //!!!


    public interface loginCallback{
        void login(String response);
    }

    public interface setMyProfileTextCallBack{
        void setProfileText(JSONArray userArray);
    }

    public interface registerCallback{
        void checkSignUpSuccess(String response);
    }



    private loginCallback loginActivity;

    private  setMyProfileTextCallBack profileActivity;

    private  registerCallback signUpActivity;

    public UserNetworker(Activity activity){

        String name =activity.getClass().getSimpleName();
        if(name.equals("LoginActivity")) {
            loginActivity = (loginCallback) activity;
            Log.d("ER í login",name);
        }
        else if(name.equals("ProfileActivity")) {
            profileActivity = (setMyProfileTextCallBack) activity;
            Log.d("er í profile",name);
        }
        else if(name.equals("SignUpActivity")) {
            signUpActivity = (registerCallback) activity;
            Log.d("er í signup",name);
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


        JsonArrayRequest req = new JsonArrayRequest("http://130.208.151.228:8181/mobile_myProfile?username="+username,

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
        //New user created
        public void register(HashMap<String,String> registerInfo){

            JSONObject jsonBody = new JSONObject(registerInfo);
            final String requestBody = jsonBody.toString();
            final String[] check = {null};
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://130.208.151.228:8181/mobile_register", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                    signUpActivity.checkSignUpSuccess(check[0]);

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
                        if(responseString.equals("200")) {
                            check[0] = "true";
                            // can get more details such as response.headers
                        }
                        else check[0]="false";
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(stringRequest);
        }
    //Checks if a new user creates valid credentials
    public void checkIfValid(final HashMap<String,String> auth) {
        String username = auth.get("username");
        String name = auth.get("name");
        String email = auth.get("email");
        String password = auth.get("password");
        String age = auth.get("age");
        String weight = auth.get("weight");

        final JsonArrayRequest req = new JsonArrayRequest("http://130.208.151.228:8181/mobile_checkIfValid?username="+username+"&name="+name+"&email="+email+
                "&password="+password+"&age="+age+"&weight="+weight,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            if(response.get(0).equals("Succesfull")) {

                                register(auth);
                            }
                            else System.out.print("-------ubs-------");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //Toast.makeText(getApplicationContext(),
                // error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        req.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

}
