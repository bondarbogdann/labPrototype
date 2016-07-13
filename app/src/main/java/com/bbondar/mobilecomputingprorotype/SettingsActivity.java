package com.bbondar.mobilecomputingprorotype;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

/**
 * Created by bbondar on 23-Jun-16.
 */
public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private boolean notificationStatus = true;
    private String notIntervalStr = "1";
    private String dataIntervalStr = "100";

    public static final String NOTIFICATION_STATUS = "NOTIFICATION_STATUS";
    public static final String NOTIFICATION_INTERVAL = "NOTIFICATION_INTERVAL";
    public static final String DATA_INTERVAL = "DATA_INTERVAL";
    public static final String ROTATION_LIMIT = "ROTATION_LIMIT";
    public static final String BAND_LIMIT = "BAND_LIMIT";
    public static final String LEAN_LIMIT = "LEAN_LIMIT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        final Switch notStatus = (Switch) findViewById(R.id.notificationStatus);
        notStatus.setOnCheckedChangeListener(this);
        Spinner notInterval = (Spinner) findViewById(R.id.notInterval);
        notInterval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                notIntervalStr = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.notification_interval, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        notInterval.setAdapter(adapter1);
        Spinner dataInterval = (Spinner) findViewById(R.id.dataInterval);
        dataInterval.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dataIntervalStr = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.data_interval, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataInterval.setAdapter(adapter2);
        final EditText rtLimit = (EditText) findViewById(R.id.rtLimit);
        final EditText bndLimit = (EditText) findViewById(R.id.bndLimit);
        final EditText lnLimit = (EditText) findViewById(R.id.lnLimit);
        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent result = new Intent();
                        result.putExtra(NOTIFICATION_STATUS, notificationStatus);
                        result.putExtra(NOTIFICATION_INTERVAL, Long.parseLong(notIntervalStr));
                        result.putExtra(DATA_INTERVAL, dataIntervalStr);
                        result.putExtra(ROTATION_LIMIT, Double.parseDouble(rtLimit.getText().toString()));
                        result.putExtra(BAND_LIMIT, Double.parseDouble(bndLimit.getText().toString()));
                        result.putExtra(LEAN_LIMIT, Double.parseDouble(lnLimit.getText().toString()));
                        setResult(Activity.RESULT_OK, result);
                        finish();
                    }
                });
            }
        });

        Button cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent result = new Intent();
                        setResult(Activity.RESULT_CANCELED, result);
                        finish();
                    }
                });
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked)
            notificationStatus = true;
        else
            notificationStatus = false;

    }
}
