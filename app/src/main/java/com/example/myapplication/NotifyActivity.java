package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.SharedElementCallback;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.AsyncListDiffer;

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

public class NotifyActivity extends AppCompatActivity implements addName {

    Button fragmentBtn;
    Button sendPush;
    SharedPreferences sharedPreferences;
    TextView textView2;

    private static final int NOTIFY_ID = 101;

    // Идентификатор канала
    private static String CHANNEL_ID = "Cat channel";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        fragmentBtn = findViewById(R.id.openFragment);
        textView2 = findViewById(R.id.textView2);
        sharedPreferences = getSharedPreferences("main", Context.MODE_PRIVATE);
        sendPush = findViewById(R.id.button4);
        fragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlankFragment dialog = new BlankFragment();
                dialog.show(getSupportFragmentManager(),"BlankFragment");
            }
        });

        sendPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("ads")
                        .setContentText("asd")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getBaseContext());

                notificationManager.notify(1, builder.build());
            }
        });


    }



    @Override
    public void add(String name) {
        textView2.setText(name);
    }

//    @Override
//    public void OnStart(boolean hasCapture) {
//        sharedPreferences = getSharedPreferences("main", Context.MODE_PRIVATE);
//        textView2.setText(sharedPreferences.getString("name","").toString());
//    }


//    @Override
//    protected void () {
//        super.onStart();
//        sharedPreferences = getSharedPreferences("main", Context.MODE_PRIVATE);
//        textView2.setText(sharedPreferences.getString("name","").toString());
//    }

}