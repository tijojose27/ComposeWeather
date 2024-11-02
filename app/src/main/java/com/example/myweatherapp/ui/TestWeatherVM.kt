package com.example.myweatherapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.data.Result
import com.example.myweatherapp.data.WeatherRepository
import com.example.myweatherapp.data.model.Weather
import com.example.myweatherapp.data.model.WeatherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TestWeatherVM(
    private val weatherRepository: WeatherRepository
): ViewModel() {

    private val _weather = MutableStateFlow(WeatherData.EMPTY)
    val  weather = _weather.asStateFlow()

    private val _showErrorToastChannel= Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()


//    fun getWeather() {
//        viewModelScope.launch {
//            weatherRepository.getWeather().collectLatest { result->
//                when(result) {
//                    is Result.Error -> {
//                        _showErrorToastChannel.send(true)
//                    }
//
//                    is Result.Success -> {
//                        result.data?.let { currWeather ->
//                            _weather.update { currWeather }
//                        }
//                    }
//                }
//            }
//        }
//    }

    fun getWeatherAsStateFlow() {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.getWeather().collectLatest { result ->
                when(result) {
                    is Result.Error -> {
                        _showErrorToastChannel.send(true)
                    }
                    is Result.Success -> {
                        result.data?.let { currWeather ->
                            _weather.update { currWeather }
                        }
                    }
                }
            }
        }
    }


    fun getError() {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.getErrro().collectLatest { result ->
                when(result) {
                    is Result.Error -> {
                        _showErrorToastChannel.send(true)
                    }

                    is Result.Success -> {

                    }
                }
            }
        }
    }
}