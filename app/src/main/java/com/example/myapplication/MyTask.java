package com.example.myapplication;

import android.os.AsyncTask;

import java.util.concurrent.TimeUnit;

public class MyTask extends AsyncTask {
    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
