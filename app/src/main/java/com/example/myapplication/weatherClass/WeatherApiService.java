package com.example.myapplication.weatherClass;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    @GET("https://api.openweathermap.org/data/2.5/weather?appid=2670bb564326d0de8b34fa11c8dc2cfc")
    Call<WeatherBody> getWeather(@Query("q") String city);
}
