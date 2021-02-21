package com.example.myapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Lists extends SQLiteOpenHelper {
    String CREATE_TABLE_LISTS = "CREATE TABLE lists " +
            "(" +
                "_id INTEGER PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "date INTEGER NOT NULL,"+
                "description TEXT"+
            ")";

    String CREATE_TABLE_TYPE = "CREATE TABLE type " +
            "(" +
            "_id INTEGER PRIMARY KEY," +
            "label TEXT NOT NULL," +
            "rule TEXT NOT NULL"+
            ")";

    String CREATE_TABLE_PRODUCT = "CREATE TABLE product " +
            "(" +
            "_id INTEGER PRIMARY KEY," +
            "name TEXT NOT NULL," +
            "checked INTERGER,"+
            "count FLOAT NOT NULL ," +
            "count_type FLOAT NOT NULL ," +
            "list_id INTEGER NOT NULL," +
            "FOREIGN KEY (list_id) " +
            "      REFERENCES list (group_id) " +
            "         ON DELETE CASCADE " +
            "         ON UPDATE NO ACTION," +
            "   FOREIGN KEY (count_type) " +
            "      REFERENCES type (count_type) " +
            "         ON DELETE CASCADE " +
            "         ON UPDATE NO ACTION"+
            ")";

    final static String DB_NAME = "testDb.db";
    static int DB_VER = 1;
    Context mcontext;


    public Lists(Context context) {
        super(context, DB_NAME, null, DB_VER);
        mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_LISTS);
        db.execSQL(CREATE_TABLE_TYPE);
        db.execSQL(CREATE_TABLE_PRODUCT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS 'lists'");
        onCreate(db);
    }
}
