package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class NameListKotlinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name_list_kotlin)
        supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.frameLayout, NameListFragment(), null)
                .commit()
    }
}