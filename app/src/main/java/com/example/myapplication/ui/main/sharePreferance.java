package com.example.myapplication.ui.main;

import android.content.Context;
import android.content.SharedPreferences;

public class sharePreferance {
    private SharedPreferences sharedPreferences;

    public void SharedFiles(Context context){
        sharedPreferences = context.getSharedPreferences("DateTime",Context.MODE_PRIVATE);
    }
    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
