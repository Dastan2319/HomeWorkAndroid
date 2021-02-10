package com.example.myapplication;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
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


            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Телефон разряжан")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            builder.create().show();
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "Battery")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Батарея")
                .setContentText(text.toString())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(1, builder.build());

//        Toast.makeText(context,batteryLevel,Toast.LENGTH_LONG).show();
    }



}
