package com.example.myweatherapp.Service

import com.example.myweatherapp.data.model.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("weather")
    suspend fun getWeather(
        @Query("appid") apiKey: String = "1f0dff223d0c8d10c9e9736c80bec010",
        @Query("lat") lat: Double = 29.565040,
        @Query("lon") lon: Double = -95.587918,
        @Query("units") units: String = "imperial"
    ): Response<WeatherData>
}