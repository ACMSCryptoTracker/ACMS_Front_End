package android.cryptocurrencytracker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class Market_Cap_Alert extends Fragment {
    Spinner coins_spin;
    TextView curr_price;
    EditText dec_value,inc_value;
    Button btnsave,btncancel;

    public Market_Cap_Alert() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_market__cap__alert, container, false);

        coins_spin = (Spinner) view.findViewById(R.id.market_cap_coins_spin);
        curr_price = (TextView) view.findViewById(R.id.set_volume_price_text);

        coins_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                final String coins = coins_spin.getSelectedItem().toString().trim();
                //connection
                String URL = "https://cryptopricetracker.herokuapp.com/getCoinData";
                JSONObject jsonBody2 = new JSONObject();
                try {
                    jsonBody2.put("coin",coins);
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
                                curr_price.setText(""+data.getDouble("market_cap_usd"));
                                //Toast.makeText(getContext(), "Successfully Set Alert", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getContext(), "Something Wrong Happens", Toast.LENGTH_SHORT).show();
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
                MySingleton.getInstance(getActivity()).addToRequestQueue(jsObjRequest2);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        curr_price = (TextView) view.findViewById(R.id.set_market_cap_price_text);

        dec_value =(EditText) view.findViewById(R.id.market_cap_dec);
        inc_value = (EditText) view.findViewById(R.id.market_cap_inc);

        btnsave =(Button) view.findViewById(R.id.market_cap_alert_save_btn);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_market_cap_save_alert();
            }
        });
        btncancel =(Button) view.findViewById(R.id.market_cap_alert_cancel_btn);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_market_cap_cancel_alert();
            }
        });
        return view;
    }
    public void do_market_cap_save_alert()
    {
        final Double dec_val = Double.parseDouble(dec_value.getText().toString().trim());
        final Double inc_val = Double.parseDouble(inc_value.getText().toString().trim());
        final String coins = coins_spin.getSelectedItem().toString().trim();
        final Double marketcap = Double.parseDouble(curr_price.getText().toString().trim());

        final int userid = 2;
        String AlertType="MARKETCAP_ALERT";
        String conversionSym="USD";

        if(dec_value.getText().toString().isEmpty()==true)
        {
            Toast.makeText(getContext(), "Decrease value Field can not be empty!", Toast.LENGTH_SHORT).show();
        }
        if(dec_value.getText().toString().isEmpty()==true)
        {
            Toast.makeText(getContext(), "Increase value Field can not be empty!", Toast.LENGTH_SHORT).show();
        }
        //connection
        String URL = "https://cryptopricetracker.herokuapp.com/setAlert";
        JSONObject jsonBody2 = new JSONObject();
        try {
            jsonBody2.put("userid",userid);
            jsonBody2.put("type",AlertType);
            jsonBody2.put("currency_symbol",coins);
            jsonBody2.put("conversion_symbol",conversionSym);
            jsonBody2.put("marketcap",marketcap);
            jsonBody2.put("marketcap_inc_by",dec_val);
            jsonBody2.put("marketcap_dec_by",inc_val);
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
                        Toast.makeText(getContext(), "Successfully Set Alert", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getContext(), "Something Wrong Happens", Toast.LENGTH_SHORT).show();
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
        MySingleton.getInstance(getActivity()).addToRequestQueue(jsObjRequest2);
    }
    public void do_market_cap_cancel_alert()
    {

    }

}
