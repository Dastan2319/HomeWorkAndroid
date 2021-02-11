package com.example.myapplication;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.PowerManager;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentManager;

public class BattaryBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        int batteryLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||  status == BatteryManager.BATTERY_STATUS_FULL;
        SharedPreferences sharedPref = context.getSharedPreferences("battery",Context.MODE_PRIVATE);

        Boolean sound = sharedPref.getBoolean("sound", false);
        Boolean vibration = sharedPref.getBoolean("vibration", false);
        Boolean dialogSwitch = sharedPref.getBoolean("dialogSwitch", false);


        String text = "";
        if(isCharging && batteryLevel < 100){
            text = "Питание подключено - " + batteryLevel + "%";
        }

        if(isCharging && batteryLevel == 100){
            text = "Полностью заряжена  - 100% ";
        }

        if(!isCharging && batteryLevel > 15){
            text = "Уровень заряда - " + batteryLevel + "%";
        }

        if(!isCharging && batteryLevel < 15){
            text = "Почти разряжена  - " + batteryLevel + "%";
            Settings.System.putInt(context.getContentResolver(),Settings.System.SCREEN_BRIGHTNESS,10);
            if(dialogSwitch) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Телефон разряжан")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                builder.create().show();
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Battery")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Батарея")
                .setContentText(text.toString())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if(sound){
            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            builder.setSound(uri);
        }
        if (vibration){
            builder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        }
        notificationManager.notify(1, builder.build());

//        Toast.makeText(context,batteryLevel,Toast.LENGTH_LONG).show();
    }



}
