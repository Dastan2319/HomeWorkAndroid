package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;

public class OpenWeatherActivity extends AppCompatActivity {

    String[] cities = {"Karaganda","Almaty","Dzhezkazgan"};
    String APP_KEY_ID = "appid=2670bb564326d0de8b34fa11c8dc2cfc/";
    static String API = "https://api.openweathermap.org/data/2.5/weather/";
    String url=API+cities[0]+"&="+APP_KEY_ID;
    TextView textView;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_weather);
        textView = findViewById(R.id.textView4);
        spinner = findViewById(R.id.spinner3);


        ArrayAdapter<String> adapter = new ArrayAdapter(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               getWeatherByCity(cities[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });


    }
    public void getWeatherByCity(String city){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<Weather> get = service.getWeather(city);

        get.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
//                textView
                if(response.isSuccessful()) {
                    textView.setText("Name:"+response.body().name + "  Pressure:"+response.body().main.pressure + "  Temp:"+response.body().main.temp);
                    Log.i("getRetrofit", String.valueOf(response.body()));
                }

            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.i("getRetrofitError",t.getMessage().toString());
            }
        });
    }
    public interface APIService {
        @GET("https://api.openweathermap.org/data/2.5/weather?appid=2670bb564326d0de8b34fa11c8dc2cfc")
        Call<Weather> getWeather(@Query("q") String city);
    }

    public class Weather{
        @SerializedName("name")
        private String name;
        @SerializedName("main")
        private Main main;
//        @SerializedName("pressure")
//        private Double pressure;

    }
    class Main {
        @SerializedName("temp")
        public float temp;
        @SerializedName("humidity")
        public float humidity;
        @SerializedName("pressure")
        public float pressure;
        @SerializedName("temp_min")
        public float temp_min;
        @SerializedName("temp_max")
        public float temp_max;
    }
}