package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class BroadcastReceiverActivity extends AppCompatActivity {

    Button start;
    Button stop;

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch sound;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch vibration;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch dialogSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_receiver);

        start = findViewById(R.id.startCheck);
        stop = findViewById(R.id.stopCheck);
        sound = findViewById(R.id.sound);
        vibration = findViewById(R.id.vibration);
        dialogSwitch = findViewById(R.id.dialogSwitch);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("battery",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("sound", isChecked);
                editor.apply();
            }
        });

        vibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("vibration", isChecked);
                editor.apply();
            }
        });

        dialogSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("dialogSwitch", isChecked);
                editor.apply();
            }
        });

        BroadcastReceiver broadcastReceiver = new BattaryBroadcastReceiver();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BroadcastReceiverActivity.this.registerReceiver(broadcastReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unregisterReceiver(broadcastReceiver);
            }
        });
    }

//    public int getBooleanToInt
}