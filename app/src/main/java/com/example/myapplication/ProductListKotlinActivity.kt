package com.example.myapplication

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.db.Lists
import com.example.myapplication.db.Product
import com.example.myapplication.db.Type
import java.util.*

class ProductListKotlinActivity : AppCompatActivity() {

    var spinner: Spinner? = null
    var spinner2: Spinner? = null

    var dbLists: Lists? = null
    var dbType: Type? = null
    var dbProduct: Product? = null

    var listView: ListView? = null
    var button: Button? = null
    var editText: EditText? = null
    var count: EditText? = null
    var selectedList: Int? = null
    var selectedType: Int? = null

    var itemName: ArrayList<String>? = null
    var itemIds: ArrayList<Int>? = null
    var itemType: ArrayList<String>? = null
    var itemIdsType: ArrayList<Int>? = null
    var itemProduct: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list_kotlin)


        count = findViewById<EditText>(R.id.count)
        editText = findViewById<EditText>(R.id.productName)
        button = findViewById<Button>(R.id.button5)
        listView = findViewById<ListView>(R.id.listView)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            dbLists = Lists(applicationContext)
//            setBaseTypeToDataBase(dbLists);
//            setBaseListsToDataBase(dbLists);
        }


        spinner = findViewById<Spinner>(R.id.spinner)
        spinner2 = findViewById<Spinner>(R.id.spinner2)


        val dblist: SQLiteDatabase? = dbLists?.getReadableDatabase();

        val projection = arrayOf(
                "_id",
                "name"
        )
        val selection = "name"
        val selectionArgs = arrayOf<String>()
        val sortOrder = ""
        val cursor = dblist?.query("lists", projection, null, null, null, null, sortOrder)




        itemName = ArrayList<String>()
        itemIds = ArrayList<Int>()

        if (cursor != null) {
            while (cursor.moveToNext()) {
                val itemId = cursor.getInt(cursor.getColumnIndexOrThrow("_id"))
                val item = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                itemName!!.add(item)
                itemIds!!.add(itemId)
            }
        }
        if (cursor != null) {
            cursor.close()
        }
        val adapter: ArrayAdapter<Any?> = ArrayAdapter<Any?>(baseContext, android.R.layout.simple_spinner_dropdown_item, itemName!! as List<Any?>)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.setAdapter(adapter)

        spinner?.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                selectedList = itemIds!!.get(position)
                if (dblist != null) {
                    setProductByListS(dblist)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }
        })


        val projectionType = arrayOf(
                "_id",
                "label",
                "rule"
        )
        val cursorType = dblist?.query("type", projectionType, null, null, null, null, sortOrder)

        itemType = ArrayList<String>()
        itemIdsType = ArrayList<Int>()
        if (cursorType != null) {
            while (cursorType.moveToNext()) {
                val itemId = cursorType.getInt(cursorType.getColumnIndexOrThrow("_id"))
                val item = cursorType.getString(cursorType.getColumnIndexOrThrow("label"))
                itemType!!.add(item)
                itemIdsType!!.add(itemId)
            }
        }
        if (cursorType != null) {
            cursorType.close()
        }
        val adapterType: ArrayAdapter<Any?> = ArrayAdapter<Any?>(baseContext, android.R.layout.simple_spinner_dropdown_item, itemType!! as List<Any?>)
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2?.setAdapter(adapterType)


        spinner2?.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                selectedType = itemIdsType!!.get(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }
        })


        button?.setOnClickListener(View.OnClickListener {
            val product: SQLiteDatabase? = dbLists?.getWritableDatabase()
            val values = ContentValues()
            values.put("name", editText?.getText().toString())
            values.put("checked", 0)
            values.put("count", count?.getText().toString())
            values.put("count_type", selectedType)
            values.put("list_id", selectedList)
            if (product != null) {
                product.insert("product", null, values)
            }
            if (dblist != null) {
                setProductByListS(dblist)
            }
        })

    }

    private fun setProductByListS(dblist: SQLiteDatabase) {
        val projectionProduct = arrayOf(
                "count",
                "name",
                "count_type",
                "list_id"
        )
        val selectionProduct = "list_id=$selectedList"
        val cursorProduct = dblist.query("product", projectionProduct, selectionProduct, null, null, null, null)
        itemProduct = ArrayList<String>()
        while (cursorProduct.moveToNext()) {
            val count = cursorProduct.getInt(cursorProduct.getColumnIndexOrThrow("count"))
            val item = cursorProduct.getString(cursorProduct.getColumnIndexOrThrow("name"))
            val count_type = cursorProduct.getInt(cursorProduct.getColumnIndexOrThrow("count_type"))
            itemProduct!!.add("Name:" + item + " count:" + count + " " + (itemType?.get(count_type)))
        }
        cursorProduct.close()
        val adapterProduct: ArrayAdapter<Any?> = ArrayAdapter<Any?>(baseContext, android.R.layout.simple_selectable_list_item, itemProduct!! as List<Any?>)
        adapterProduct.setDropDownViewResource(android.R.layout.simple_selectable_list_item)
        listView?.setAdapter(adapterProduct)
    }

    private fun setBaseListsToDataBase(dbLists: Lists) {
        val list = dbLists.writableDatabase
        val values = ContentValues()
        values.put("name", "List 1")
        values.put("date", 1)
        values.put("description", "1")

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
        list.insert("lists", null, values)
//        list.insert("lists",null,values1);
//        list.insert("lists",null,values2);
    }

    private fun setBaseTypeToDataBase(dbType: Lists) {
        val type = dbType.writableDatabase
        val values = ContentValues()
        values.put("label", "шт")
        values.put("rule", "int")
        val values1 = ContentValues()
        values1.put("label", "кг")
        values1.put("rule", "float")
        val values2 = ContentValues()
        values2.put("label", "л")
        values2.put("rule", "float")
        type.insert("type", null, values)
        type.insert("type", null, values1)
        type.insert("type", null, values2)
    }
}
