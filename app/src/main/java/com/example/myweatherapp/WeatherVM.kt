package com.example.myweatherapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.Network.NetworkResponse
import com.example.myweatherapp.Service.RetrofitInstance
import com.example.myweatherapp.data.WeatherData
import kotlinx.coroutines.launch

class WeatherVM : ViewModel() {

    private val weatherAPI = RetrofitInstance.weatherAPI
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherData>>()
    val weatherResult: LiveData<NetworkResponse<WeatherData>> = _weatherResult


    init {
        val fullUrl = RetrofitInstance.getFullUrl(
            endpoint = "weather",
            queryParams = mapOf(
                "q" to "London",
                "appid" to "your_api_key"
            )
        )

        Log.e("VM DEBUG GET URL", fullUrl)  // Output: https://api.openweathermap.org/data/2.5/weather?q=London&appid=your_api_key
        getData()

    }

    //use : https://www.figma.com/design/LfxPlArXOlJ74YNfQwpz8s/SALY---3D-Illustration-Pack-(Community)?node-id=7-4&node-type=canvas
    //use : https://www.figma.com/design/kCYEnx8j7LCxOiBcr2sjFM/Weatherly-3D-Icons---Demo-version-(Community)?node-id=0-10
    //use : https://github.com/Mercandj/android-dev-challenge-compose-4
    fun getWeatherIcon(iconName: String): Int {
        return when(iconName) {
            "01d.png", "01n.png" -> R.drawable.sunny        //clear sky
            "02d.png", "02n.png" -> R.drawable.cloudy_sunny //few clouds
            "03d.png", "03n.png" -> R.drawable.cloudy       //scattered clouds
            "04d.png", "04n.png" -> R.drawable.cloudy       //broken clouds
            "09d.png", "09n.png" -> R.drawable.rainy        //shower rain
            "10d.png", "10n.png" -> R.drawable.rain         //rain
            "11d.png", "11n.png" -> R.drawable.wind         //this is thunder
            "13d.png", "13n.png" -> R.drawable.snowy        //snowy
            "50d.png", "50n.png" -> R.drawable.windy        //mist
            else -> R.drawable.cloudy_sunny
        }
    }

    fun getData(){
        _weatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = weatherAPI.getWeather()
                if (response.isSuccessful){
                    Log.e("VM DEBUG", response.body().toString())
                    response.body()?.let {
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    Log.e("VMDEBUG", response.message())
                    _weatherResult.value = NetworkResponse.Error("Failed to load data")
                }
            } catch (e: Exception){
                _weatherResult.value = NetworkResponse.Error("Failed to load data")
            }
        }

    }
}