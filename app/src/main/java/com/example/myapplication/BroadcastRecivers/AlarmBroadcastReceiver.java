package com.example.myapplication.BroadcastRecivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.AlarmActivity;
import com.example.myapplication.R;
import com.example.myapplication.weatherClass.WeatherApiService;
import com.example.myapplication.weatherClass.WeatherBody;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    public static final String APP_CITY_NAME = "app city";
    static String API = "https://api.openweathermap.org/data/2.5/weather/";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Don't panic but your time is up!!!!.",
                Toast.LENGTH_LONG).show();
        // Vibrate the mobile phone

        SharedPreferences appCityName = context.getSharedPreferences(APP_CITY_NAME, Context.MODE_PRIVATE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApiService service =  retrofit.create(WeatherApiService.class);
        Call<WeatherBody> get = service.getWeather(appCityName.getString(APP_CITY_NAME,""));
        get.enqueue(new Callback<WeatherBody>() {
            @Override
            public void onResponse(Call<WeatherBody> call, Response<WeatherBody> response) {

                if(response.body() == null){
                    Toast toast = Toast.makeText(context, "город не задан", Toast.LENGTH_LONG);
                    toast.show();
                }
                if(response.isSuccessful()) {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "c")
                            .setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentTitle(response.body().name)
                            .setContentText("Pressure:"+response.body().main.pressure + ",Temp:"+response.body().main.temp)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

                    notificationManager.notify(1, builder.build());
                }
            }

            @Override
            public void onFailure(Call<WeatherBody> call, Throwable t) {
                Log.i("getRetrofitError",t.getMessage().toString());
            }
        });
    }
}
