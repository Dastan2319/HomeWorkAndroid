package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myapplication.BroadcastRecivers.AlarmBroadcastReceiver;
import com.example.myapplication.adapters.AlarmsAdapter;
import com.example.myapplication.weatherClass.WeatherApiService;
import com.example.myapplication.weatherClass.WeatherBody;

import java.util.ArrayList;
import java.util.Calendar;

public class AlarmActivity extends AppCompatActivity {
    public static final String APP_PREFERENCES = "alarm";
    public static final String ALARM_LIST = "alarmList";
    public static final String APP_CITY_NAME = "app city";

    String APP_KEY_ID = "appid=2670bb564326d0de8b34fa11c8dc2cfc/";
    static String API = "https://api.openweathermap.org/data/2.5/weather/";

    EditText alarmName;
    Button setAlarmName;

    EditText savedCity;
    Button addCity;

    ListView alarmList;
    SharedPreferences alarms;
    FrameLayout loadingFrame;
    ArrayList<AlarmType> gettedAlarmList = new ArrayList<>();
    DialogFragment newFragment;
    AlarmManager m_alarmMgr;
    SharedPreferences appCityName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        alarms = getSharedPreferences(ALARM_LIST, Context.MODE_PRIVATE);
        appCityName = getSharedPreferences(APP_CITY_NAME, Context.MODE_PRIVATE);


        alarmName = findViewById(R.id.alarmName);
        setAlarmName = findViewById(R.id.setAlarmName);
        savedCity = findViewById(R.id.savedCity);
        addCity = findViewById(R.id.addCity);

        alarmList = findViewById(R.id.alarmList);

        loadingFrame = findViewById(R.id.loadingFrame);


        if(appCityName.contains(APP_CITY_NAME)) {
            savedCity.setText(appCityName.getString(APP_CITY_NAME, ""));
        }

        this.setAdapter();
        setAlarmName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!alarmName.getText().toString().equals("")) {
                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(AlarmActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            String newAlarm = alarmName.getText().toString() + ","+selectedHour + ","+selectedMinute + "," + true;

                            if(alarms.contains(ALARM_LIST)){
                                String allAlarms = alarms.getString(ALARM_LIST,"");
                                SharedPreferences.Editor editor = alarms.edit();
                                editor.putString(ALARM_LIST, allAlarms+";"+newAlarm);
                                editor.apply();
                            }else{
                                SharedPreferences.Editor editor = alarms.edit();
                                editor.putString(ALARM_LIST, newAlarm);
                                editor.apply();
                            }


                            AlarmManager manager = (AlarmManager)getSystemService(
                                    Context.ALARM_SERVICE);
                            Intent intent = new Intent(getBaseContext(), AlarmBroadcastReceiver.class);
                            PendingIntent m_alarmIntent = PendingIntent.getBroadcast((Context) getBaseContext(), (int) System.currentTimeMillis(), intent, 0);
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(System.currentTimeMillis());

                            calendar.add(Calendar.HOUR, selectedHour);
                            calendar.add(Calendar.MINUTE, selectedMinute);

                            long time = calendar.getTimeInMillis();

                            manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, time, m_alarmIntent);

                            if(alarms.contains(ALARM_LIST)) {
                                ArrayList<AlarmType> arrayList = new ArrayList<>();

                                String temp = alarms.getString(ALARM_LIST,"");
                                for (int i = 0; i < temp.split(";").length; i++){
                                    for (int j = 0; j < temp.split(";")[i].split(",").length; j++){
                                        String[] alarmTemp = temp.split(";")[i].split(",");
                                        arrayList.add(new AlarmType(alarmTemp[0],alarmTemp[1],alarmTemp[2],alarmTemp[3]));
                                        break;
                                    }
                                }

                                AlarmsAdapter adapter = new AlarmsAdapter(getApplicationContext(),arrayList);

                                alarmList.setAdapter(adapter);
                            }

                            alarmName.setText("");
                        }
                    }, hour, minute, true);
                    mTimePicker.show();


                }else{
                    setToast("Введите название будильника");
                }
            }
        });
        addCity.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if(!savedCity.getText().toString().equals("")) {
                    if(appCityName.contains(APP_CITY_NAME) && !appCityName.getString(APP_CITY_NAME, "").equals(savedCity.getText().toString())) {
                        loadingFrame.setVisibility(View.VISIBLE);
                        getWeatherByCityName(savedCity.getText().toString(), appCityName);
                    }else{
                        setToast("Введенный город уже выбран");
                    }

                }else{
                    setToast("Введите название города");
                }
            }
        });


    }

    public void setAdapter(){
        if(appCityName.contains(APP_CITY_NAME)) {
            savedCity.setText(appCityName.getString(APP_CITY_NAME, ""));
        }
        if(alarms.contains(ALARM_LIST)) {
            ArrayList<AlarmType> arrayList = new ArrayList<>();

            String temp = alarms.getString(ALARM_LIST,"");
            for (int i = 0; i < temp.split(";").length; i++){
                for (int j = 0; j < temp.split(";")[i].split(",").length; j++){
                    String[] alarmTemp = temp.split(";")[i].split(",");
                    arrayList.add(new AlarmType(alarmTemp[0],alarmTemp[1],alarmTemp[2],alarmTemp[3]));
                    break;
                }
            }

//            setTimeAlarm(5,59);
            AlarmsAdapter adapter = new AlarmsAdapter(this,arrayList);

            alarmList.setAdapter(adapter);
        }
    }
    public void setTimeAlarm(int hour , int minutes){
        Intent intent = new Intent(this, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), (int) System.currentTimeMillis(), intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Toast.makeText(this, "Alarm set in " + calendar.getTimeInMillis() + " seconds",
                Toast.LENGTH_LONG).show();
    }

    public void setToast(String text){
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        toast.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getWeatherByCityName(String city , SharedPreferences appCityName){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApiService service =  retrofit.create(WeatherApiService.class);
        Call<WeatherBody> get = service.getWeather(city);
        get.enqueue(new Callback<WeatherBody>() {
            @Override
            public void onResponse(Call<WeatherBody> call, Response<WeatherBody> response) {

                if(response.body() == null){
                    setToast("Такого города нет");
                }
                if(response.isSuccessful()) {
                    SharedPreferences.Editor editor = appCityName.edit();
                    editor.putString(APP_CITY_NAME, response.body().name);
                    editor.apply();
                    setToast("Сохранено и работает");
                }
                loadingFrame.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<WeatherBody> call, Throwable t) {
                loadingFrame.setVisibility(View.INVISIBLE);

                Log.i("getRetrofitError",t.getMessage().toString());
            }
        });

    }


//    public ArrayList<AlarmType> setAppPreferences(ArrayList<AlarmType> alarms, String name , String hour, String minutes){
//        Boolean exist = false;
//        for (AlarmType alarm : alarms) {
//            if(alarm.name.equals(name)){
//                alarm.hour = hour;
//                alarm.minutes = minutes;
//                exist = true;
//            }
//        }
//        if (exist) return alarms;
//
//        alarms.add(new AlarmType(name,hour,minutes));
//        return alarms;
//    }

    public class AlarmType{
        private String name;
        private String hour;
        private String minutes;
        private String isActive;


        AlarmType(String name , String hour , String minutes , String isActive){
            this.name = name;
            this.hour = hour;
            this.minutes = minutes;
            this.isActive = isActive;
        }

        public String getName() {
            return name;
        }

        public String getHour() {
            return hour;
        }

        public String getMinutes() {
            return minutes;
        }

        public String getActive() {return isActive;}

        public void setName(String name) {
            this.name = name;
        }

        public void setHour(String hour) {
            this.hour = hour;
        }

        public void setMinutes(String minutes) {
            this.minutes = minutes;
        }

        public void setActive(String active) {
            isActive = active;
        }
    }

}



