package com.example.myweatherapp.data

import com.example.myweatherapp.data.model.WeatherData
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeather(): Flow<Result<WeatherData>>
}