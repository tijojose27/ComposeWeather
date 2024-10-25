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