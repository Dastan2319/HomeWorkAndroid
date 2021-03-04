package com.example.myapplication

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CompoundButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity

class BroadcastReceiverKotlinActivity : AppCompatActivity() {

    var start: Button? = null
    var stop: Button? = null

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    var sound: Switch? = null

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    var vibration: Switch? = null

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    var dialogSwitch: Switch? = null

    @Override

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broadcast_receiver_kotlin)


        start = findViewById(R.id.startCheck)
        stop = findViewById(R.id.stopCheck)
        sound = findViewById(R.id.sound)
        vibration = findViewById(R.id.vibration)
        dialogSwitch = findViewById(R.id.dialogSwitch)

        val sharedPref = applicationContext.getSharedPreferences("battery", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        sound?.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            editor.putBoolean("sound", isChecked)
            editor.apply()
        })

        vibration?.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            editor.putBoolean("vibration", isChecked)
            editor.apply()
        })

        dialogSwitch?.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            editor.putBoolean("dialogSwitch", isChecked)
            editor.apply()
        })

        val broadcastReceiver: BroadcastReceiver = BatteryBroadcastReceiverKotlin()

        start?.setOnClickListener(View.OnClickListener { registerReceiver(broadcastReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED)) })

        stop?.setOnClickListener(View.OnClickListener { unregisterReceiver(broadcastReceiver) })
    }

}