package android.cryptocurrencytracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class CryptoCurrencyVisualization extends AppCompatActivity {

    TextView price,curr_sym;
    ImageView graph_svg;
    Spinner change_duration;
    TextView marketCap;
    TextView volume;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto_currency_visualization);

        Intent intent=getIntent();
        String symbol=intent.getStringExtra("symbol");

        curr_sym = (TextView) findViewById(R.id.text_symbol);
        price = (TextView) findViewById(R.id.text_price);
        marketCap = (TextView) findViewById(R.id.text_marketcap);
        volume = (TextView) findViewById(R.id.text_volume);

        graph_svg = (ImageView) findViewById(R.id.photo_image_view);

        change_duration = (Spinner) findViewById(R.id.spin_visual);

        curr_sym.setText(symbol);

        //connection
        String URL = "http://ec2-18-218-241-79.us-east-2.compute.amazonaws.com/getCoinData";
        JSONObject jsonBody2 = new JSONObject();
        try {
            jsonBody2.put("coin",symbol);
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
                        JSONObject data=jsonObject.getJSONObject("data");
                        price.setText(""+data.getDouble("price_usd"));
                        marketCap.setText(""+data.getDouble("market_cap_usd"));
                        volume.setText(""+data.getDouble("24h_volume_usd"));
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Something Wrong Happens", Toast.LENGTH_SHORT).show();
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
