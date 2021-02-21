package com.example.myapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Type extends SQLiteOpenHelper {

    String CREATE_TABLE = "CREATE TABLE type " +
            "(" +
            "_id INTEGER PRIMARY KEY," +
            "label TEXT NOT NULL," +
            "rule TEXT NOT NULL"+
            ")";

    final static String DB_NAME = "testDb.db";
    static int DB_VER = 1;

    Context mcontext;


    public Type(Context context) {
        super(context, DB_NAME, null, DB_VER);
        mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        db.execSQL(CREATE_TABLE);
        db.setTransactionSuccessful();
        db.endTransaction();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS 'type'");
        onCreate(db);
    }
}
