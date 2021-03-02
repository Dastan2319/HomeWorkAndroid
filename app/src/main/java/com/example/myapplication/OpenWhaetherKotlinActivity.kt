package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class OpenWhaetherKotlinActivity : AppCompatActivity() {

    var cities = arrayOf<String?>("Karaganda", "Almaty", "Dzhezkazgan")
    var APP_KEY_ID = "appid=2670bb564326d0de8b34fa11c8dc2cfc/"
    var API = "https://api.openweathermap.org/data/2.5/weather/"
    var url = API + cities[0] + "&=" + APP_KEY_ID
    var textView: TextView? = null
    var spinner: Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_whaether_kotlin)
        textView = findViewById(R.id.textView4)
        spinner = findViewById(R.id.spinner3)
        val adapter: ArrayAdapter<Any?> = ArrayAdapter<Any?>(baseContext, android.R.layout.simple_spinner_dropdown_item, cities)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.setAdapter(adapter)
        spinner?.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                getWeatherByCity(cities[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                return
            }
        })
    }


    fun getWeatherByCity(city: String?) {
        val retrofit = Retrofit.Builder()
                .baseUrl(API)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(APIService::class.java)
        val get = service.getWeather(city)
        get.enqueue(object : Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
//                textView
                if (response.isSuccessful) {
                    textView!!.text = "Name:" + response.body()!!.name + "  Pressure:" + response.body()!!.main!!.pressure + "  Temp:" + response.body()!!.main!!.temp
                    Log.i("getRetrofit", response.body().toString())
                }
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                Log.i("getRetrofitError", t.message.toString())
            }
        })
    }

    interface APIService {
        @GET("https://api.openweathermap.org/data/2.5/weather?appid=2670bb564326d0de8b34fa11c8dc2cfc")
        fun getWeather(@Query("q") city: String?): Call<Weather>
    }

    class Weather {
        @SerializedName("name")
        val name: String? = null

        @SerializedName("main")
        internal val main: Main? = null //        @SerializedName("pressure")
        //        private Double pressure;
    }

    internal class Main {
        @SerializedName("temp")
        var temp = 0f

        @SerializedName("humidity")
        var humidity = 0f

        @SerializedName("pressure")
        var pressure = 0f

        @SerializedName("temp_min")
        var temp_min = 0f

        @SerializedName("temp_max")
        var temp_max = 0f
    }
}