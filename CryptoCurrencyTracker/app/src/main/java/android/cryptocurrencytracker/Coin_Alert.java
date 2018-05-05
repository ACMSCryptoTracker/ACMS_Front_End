package android.cryptocurrencytracker;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;


public class Coin_Alert extends Fragment {

    TimePicker coinAlertTimePicker;
    Spinner coin_spin,freq_spin;
    Button btnsave,btncancel;


    public Coin_Alert() {
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
        System.out.println("fragment added");

        View view = inflater.inflate(R.layout.fragment_coin__alert, container, false);

        coin_spin = (Spinner) view.findViewById(R.id.coin_alert_coins_spin);
        coin_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        freq_spin = (Spinner) view.findViewById(R.id.coin_alert_freq_spin);
        freq_spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        coinAlertTimePicker =(TimePicker) view.findViewById(R.id.coin_alert_time_picker);

        btnsave = (Button) view.findViewById(R.id.coin_alert_save_btn);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_coin_save_alert();
            }
        });

        btncancel = (Button) view.findViewById(R.id.coin_alert_cancel_btn);
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_coin_cancel_alert();
            }
        });

        return view;
    }

    public void do_coin_save_alert()
    {

    }
    public void do_coin_cancel_alert()
    {

    }

}
