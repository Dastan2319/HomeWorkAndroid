package com.example.myapplication

import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.BatteryManager
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class BatteryBroadcastReceiverKotlin : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
        val batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
        val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
        val sharedPref = context.getSharedPreferences("battery", Context.MODE_PRIVATE)
        val sound = sharedPref.getBoolean("sound", false)
        val vibration = sharedPref.getBoolean("vibration", false)
        val dialogSwitch = sharedPref.getBoolean("dialogSwitch", false)
        var text = ""
        if (isCharging && batteryLevel < 100) {
            text = "Питание подключено - $batteryLevel%"
        }
        if (isCharging && batteryLevel == 100) {
            text = "Полностью заряжена  - 100% "
        }
        if (!isCharging && batteryLevel > 15) {
            text = "Уровень заряда - $batteryLevel%"
        }
        if (!isCharging && batteryLevel < 15) {
            text = "Почти разряжена  - $batteryLevel%"
            Settings.System.putInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS, 10)
            if (dialogSwitch) {
                val builder = AlertDialog.Builder(context)
                builder.setMessage("Телефон разряжан")
                        .setPositiveButton("Ok") { dialog, id -> }
                builder.create().show()
            }
        }
        val builder = NotificationCompat.Builder(context, "Battery")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Батарея")
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val notificationManager = NotificationManagerCompat.from(context)
        if (sound) {
            val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            builder.setSound(uri)
        }
        if (vibration) {
            builder.setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
        }
        notificationManager.notify(1, builder.build())

    }

}