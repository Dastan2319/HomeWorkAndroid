package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.db.Lists;
import com.example.myapplication.db.Product;
import com.example.myapplication.db.Type;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.security.AccessController.getContext;

public class ProductListActivity extends AppCompatActivity {

    Spinner spinner;
    Spinner spinner2;

    Lists dbLists;
    Type dbType;
    Product dbProduct;

    ListView listView;
    Button button;
    EditText editText;
    EditText count;
    Integer selectedList;
    Integer selectedType;

    ArrayList<String> itemName;
    ArrayList<Integer> itemIds;
    ArrayList<String> itemType;
    ArrayList<Integer> itemIdsType;
    ArrayList<String> itemProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        count = findViewById(R.id.count);
        editText = findViewById(R.id.productName);
        button = findViewById(R.id.button5);
        listView = findViewById(R.id.listView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
             dbLists = new Lists(getApplicationContext());
//            setBaseTypeToDataBase(dbLists);
//            setBaseListsToDataBase(dbLists);
        }


        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);



        SQLiteDatabase dblist = dbLists.getReadableDatabase();

        String[] projection = {
                "_id",
                "name"
        };
        String selection = "name";
        String[] selectionArgs = {  };
        String sortOrder ="";
        Cursor cursor = dblist.query("lists",projection,null,null,null,null,sortOrder);




        itemName = new ArrayList<>();
        itemIds = new ArrayList<>();

        while(cursor.moveToNext()) {
            Integer itemId = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
            String item = cursor.getString(cursor.getColumnIndexOrThrow("name"));

            itemName.add(item);
            itemIds.add(itemId);
        }
        cursor.close();
        ArrayAdapter<String> adapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, itemName);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedList = itemIds.get(position);
                setProductByListS(dblist);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });



        String[] projectionType = {
                "_id",
                "label",
                "rule"
        };
        Cursor cursorType = dblist.query("type",projectionType,null,null,null,null,sortOrder);

        itemType = new ArrayList<>();
        itemIdsType = new ArrayList<>();
        while(cursorType.moveToNext()) {
            Integer itemId = cursorType.getInt(cursorType.getColumnIndexOrThrow("_id"));
            String item = cursorType.getString(cursorType.getColumnIndexOrThrow("label"));

            itemType.add(item);
            itemIdsType.add(itemId);
        }
        cursorType.close();
        ArrayAdapter<String> adapterType = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, itemType);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapterType);


        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedType = itemIdsType.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase product = dbLists.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("name", editText.getText().toString());
                values.put("checked", 0);
                values.put("count", count.getText().toString());
                values.put("count_type", selectedType);
                values.put("list_id", selectedList);

                product.insert("product",null,values);
                setProductByListS(dblist);
            }
        });

    }

    private void setProductByListS(SQLiteDatabase dblist){
        String[] projectionProduct = {
                "count",
                "name",
                "count_type",
                "list_id"
        };
        String selectionProduct = "list_id=" + selectedList;
        Cursor cursorProduct = dblist.query("product",projectionProduct,selectionProduct,null,null,null,null);

        itemProduct = new ArrayList<>();

        while(cursorProduct.moveToNext()) {
            Integer count = cursorProduct.getInt(cursorProduct.getColumnIndexOrThrow("count"));
            String item = cursorProduct.getString(cursorProduct.getColumnIndexOrThrow("name"));
            Integer count_type = cursorProduct.getInt(cursorProduct.getColumnIndexOrThrow("count_type"));
            itemProduct.add("Name:" + item + " count:" + count + " "+itemType.get(count_type));
        }
        cursorProduct.close();

        ArrayAdapter<String> adapterProduct = new ArrayAdapter(getBaseContext(), android.R.layout.simple_selectable_list_item, itemProduct);
        adapterProduct.setDropDownViewResource(android.R.layout.simple_selectable_list_item);

        listView.setAdapter(adapterProduct);
    }

    private  void setBaseListsToDataBase(Lists dbLists){
        SQLiteDatabase list = dbLists.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", "List 1");
        values.put("date", 1);
        values.put("description", "1");

//
//        ContentValues values1 = new ContentValues();
//        values1.put("name", "List 2");
//        values1.put("date", 1);
//        values1.put("description", "2");
//
//        ContentValues values2 = new ContentValues();
//        values2.put("name", "List 3");
//        values2.put("date", 1);
//        values2.put("description", "3");

        list.insert("lists",null,values);
//        list.insert("lists",null,values1);
//        list.insert("lists",null,values2);

    }

    private void setBaseTypeToDataBase(Lists dbType){
        SQLiteDatabase type = dbType.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("label", "шт");
        values.put("rule", "int");

        ContentValues values1 = new ContentValues();
        values1.put("label", "кг");
        values1.put("rule", "float");

        ContentValues values2 = new ContentValues();
        values2.put("label", "л");
        values2.put("rule", "float");

        type.insert("type",null,values);
        type.insert("type",null,values1);
        type.insert("type",null,values2);
    }

}