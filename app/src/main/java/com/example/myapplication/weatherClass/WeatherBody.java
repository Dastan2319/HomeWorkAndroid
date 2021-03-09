package com.example.myapplication.weatherClass;

import com.google.gson.annotations.SerializedName;

public class WeatherBody {
    @SerializedName("name")
    public String name;
    @SerializedName("main")
    public MainBody main;
}

