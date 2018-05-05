package android.cryptocurrencytracker;

import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    EditText firstname,lastname,email,password;
    TextView errorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstname = (EditText) findViewById(R.id.signup_first);
        lastname = (EditText) findViewById(R.id.signup_last);
        email = (EditText) findViewById(R.id.signup_email);
        password = (EditText) findViewById(R.id.signup_pass);

        errorMsg = (TextView) findViewById(R.id.signup_error_msg);
    }
    public void doSignin(View view)
    {
        Intent it=new Intent(this, LogIn.class);
        startActivity(it);
        Toast.makeText(this, "LogIn", Toast.LENGTH_SHORT).show();
    }
    public void doSignup(View view)
    {

        final String first=firstname.getText().toString().trim();
        final String last=lastname.getText().toString().trim();
        final String pass=password.getText().toString().trim();
        final String Semail=email.getText().toString().trim();
        if(first.isEmpty()==true)
        {
            Toast.makeText(this, "Firstname Field can not be empty!", Toast.LENGTH_SHORT).show();
        }
        if(last.isEmpty()==true)
        {
            Toast.makeText(this, "Lastname Field can not be empty!", Toast.LENGTH_SHORT).show();
        }
        if(pass.isEmpty()==true)
        {
            Toast.makeText(this, "Password Field can not be empty!", Toast.LENGTH_SHORT).show();
        }
        if(Semail.isEmpty()==true)
        {
            Toast.makeText(this, "Email Id Field can not be empty!", Toast.LENGTH_SHORT).show();
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Semail).matches())
        {
            Toast.makeText(this, "Email Pattern is not correct", Toast.LENGTH_SHORT).show();
        }

        //connection
        String URL = "http://ec2-18-218-241-79.us-east-2.compute.amazonaws.com/registeration";
        JSONObject jsonBody2 = new JSONObject();
        try {
            jsonBody2.put("name",first +" "+ last);
            jsonBody2.put("email",Semail);
            jsonBody2.put("password",pass);
            //Log.i("email",email);
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
                    if(first.isEmpty()==false && last.isEmpty()==false && pass.isEmpty()==false && Semail.isEmpty()==false
                            && Patterns.EMAIL_ADDRESS.matcher(Semail).matches())
                    {
                        if(Success==1)
                        {
                            Intent it = new Intent(getApplicationContext(), List_Of_Cryptocurrency.class);
                            startActivity(it);
                            Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            errorMsg.setVisibility(View.VISIBLE);
                            errorMsg.setText("Registration Failed");
                        }
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
