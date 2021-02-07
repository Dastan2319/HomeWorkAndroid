package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class SharedPreverenceActivity extends AppCompatActivity {

    TextView tx;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preverence);
        sharedPreferences = getApplicationContext().getSharedPreferences("dateTime",MODE_PRIVATE);
        sharedPreferences.edit().putString("name",tx.getText().toString()).apply();
    }
}