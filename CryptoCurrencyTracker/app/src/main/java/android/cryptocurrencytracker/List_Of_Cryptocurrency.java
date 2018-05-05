package android.cryptocurrencytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class List_Of_Cryptocurrency extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    RecyclerView recyclerView;
    Recycler_View_Adapter adapter;


    List<Recycler_View_Class> recycler_view_classList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__of__cryptocurrency);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent it = new Intent(getApplication(), Alerts.class);
                startActivity(it);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        //connection
        String URL = "http://ec2-18-218-241-79.us-east-2.compute.amazonaws.com/currentData";

        final StringRequest jsObjRequest2 = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
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
                        JSONArray data=jsonObject.getJSONArray("data");
                        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recycler_view_classList = new ArrayList<>();
                        for(int i=0;i<data.length();i++)
                        {

                            JSONObject jobj=data.getJSONObject(i);
                            String name =jobj.getString("name");

                            recycler_view_classList.add(new Recycler_View_Class(
                                    i+1,
                                    R.drawable.bitcoin,
                                    jobj.getString("name"),
                                    jobj.getString("symbol"),
                                    Double.parseDouble(jobj.getString("price_usd")),
                                    Double.parseDouble(jobj.getString("percent_change_24h"
                                    ))));
                        }
                        Recycler_View_Adapter adapter=new Recycler_View_Adapter(getApplicationContext(), recycler_view_classList);
                        recyclerView.setAdapter(adapter);
                        adapter.setOnItemClickListener(new Recycler_View_Adapter.ClickListener() {
                            @Override
                            public void onItemClick(int position, View v) {
                                Recycler_View_Class recycler_view_class=recycler_view_classList.get(position);
                                Log.i("sym",recycler_view_class.getCrypto_symbol());
                                Intent intent= new Intent(getApplication(),CryptoCurrencyVisualization.class);
                                intent.putExtra("symbol",recycler_view_class.getCrypto_symbol());
                                startActivity(intent);
                            }
                        });

                    }
                    else
                    {
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

        });

        jsObjRequest2.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsObjRequest2);

        //recycler view linear layout manager is by default vertical


       /* recycler_view_classList = new ArrayList<>();

        recycler_view_classList.add(
                new Recycler_View_Class(
                        1,
                         R.drawable.bitcoin,
                        "Bitcoin",
                        "BTC",
                        87175.5,
                        5.96
                ));
        recycler_view_classList.add(
                new Recycler_View_Class(
                        2,
                        R.drawable.ethereum,
                        "Ethereum",
                        "ETH",
                        87175.5,
                        5.96
                ));
        recycler_view_classList.add(
                new Recycler_View_Class(
                        3,
                        R.drawable.litecoin,
                        "Litecoin",
                        "LTC",
                        87175.5,
                        5.96
                ));
        recycler_view_classList.add(
                new Recycler_View_Class(
                        4,
                        R.drawable.bitcoin_cash,
                        "Bitcoin Cash",
                        "BCH",
                        87175.5,
                        5.96
                ));
        recycler_view_classList.add(
                new Recycler_View_Class(
                        5,
                        R.drawable.ripple,
                        "Ripple",
                        "XRP",
                        87175.5,
                        5.96
                ));
*/



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list__of__cryptocurrency, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
        } else if (id == R.id.nav_Alerts) {
            Intent it=new Intent(this, Alerts.class);
            startActivity(it);
            Toast.makeText(this, "Alerts", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_aboutus) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
