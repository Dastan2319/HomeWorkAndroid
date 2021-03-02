package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotifyKotlinActivity : AppCompatActivity(), addNameKotlin {
    var fragmentBtn: Button? = null
    var sendPush: Button? = null
    var sharedPreferences: SharedPreferences? = null
    var textView2: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notify)
        fragmentBtn = findViewById(R.id.openFragment)
        textView2 = findViewById(R.id.textView2)
        sharedPreferences = getSharedPreferences("main", Context.MODE_PRIVATE)
        sendPush = findViewById(R.id.button4)
        fragmentBtn?.setOnClickListener(View.OnClickListener {
            val dialog = NotifyFragment()
            dialog.show(supportFragmentManager, "NotifyFragment")
        })
        sendPush?.setOnClickListener(View.OnClickListener {
            val builder = NotificationCompat.Builder(baseContext, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher_background)
                    .setContentTitle("ads")
                    .setContentText(textView2.toString())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            val notificationManager = NotificationManagerCompat.from(baseContext)
            notificationManager.notify(1, builder.build())
//            val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext)
//                    .setSmallIcon(android.R.drawable.ic_dialog_email)
//                    .setContentTitle("Title")
//                    .setContentText(textView2.toString())
//                    .setOngoing(true)
//                    .setChannelId(CHANNEL_ID)
        })
    }

    override fun add(name: String?) {
        textView2!!.text = name
    } //    @Override

    companion object {
        private const val NOTIFY_ID = 101

        // Идентификатор канала
        private const val CHANNEL_ID = "Cat channel"
    }



}
interface addNameKotlin {
    fun add(name: String?)
}