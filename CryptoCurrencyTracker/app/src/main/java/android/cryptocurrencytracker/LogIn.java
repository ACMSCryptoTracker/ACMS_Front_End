package android.cryptocurrencytracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class LogIn extends AppCompatActivity{

   EditText userid;
   EditText pass;

   TextView errorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        userid = (EditText) findViewById(R.id.login_userid);
        pass = (EditText) findViewById(R.id.login_Password);
        errorMsg = (TextView) findViewById(R.id.login_error_msg);

    }

    public void doSignup(View view)
    {
        Intent it=new Intent(this, SignUp.class);
        startActivity(it);
        Toast.makeText(this, "SignUp", Toast.LENGTH_SHORT).show();
    }

    public void login_btn(View view)
    {
        String Luserid=userid.getText().toString();
        String Lpassword=pass.getText().toString();

        if(Luserid.isEmpty()==true)
        {
            Toast.makeText(this, "Email Id field can not be empty", Toast.LENGTH_SHORT).show();
        }
        if(Lpassword.isEmpty()==true)
        {
            Toast.makeText(this, "Password field can not be empty", Toast.LENGTH_SHORT).show();
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Luserid).matches())
        {
            Toast.makeText(this, "Email Pattern is not correct", Toast.LENGTH_SHORT).show();
        }

        //connection
        String URL = "https://cryptopricetracker.herokuapp.com/login";
        JSONObject jsonBody2 = new JSONObject();
        try {
            jsonBody2.put("email",Luserid);
            jsonBody2.put("password",Lpassword);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String mRequestBody2 = jsonBody2.toString();
        final StringRequest jsObjRequest2 = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

                try
                {
                    Log.i("response",response);
                    JSONObject jsonObject=new JSONObject(response);
                    final int Success = jsonObject.getInt("Success");
                    if(Success==1)
                    {
                            Intent it=new Intent(getApplicationContext(), List_Of_Cryptocurrency.class);
                            startActivity(it);
                            Toast.makeText(getApplicationContext(), "List Of Cryptocurrencies", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        errorMsg.setVisibility(View.VISIBLE);
                        errorMsg.setText("Either Email Id or Password is incorrect");
                    }
                }

                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("error",new String(error.networkResponse.data));
            }

        })
        {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody2 == null ? null : mRequestBody2.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody2, "utf-8");
                    return null;
                }
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json");

                return params;
            }
        };
        jsObjRequest2.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsObjRequest2);
    }
}