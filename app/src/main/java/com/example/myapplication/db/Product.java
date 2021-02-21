package com.example.myapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.AccessControlContext;

import androidx.annotation.Nullable;

public class Product extends SQLiteOpenHelper {

    String CREATE_TABLE = "CREATE TABLE product " +
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

    public Product(Context context) {
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
        db.execSQL("DROP TABLE IF EXISTS 'product'");
        onCreate(db);
    }
}
