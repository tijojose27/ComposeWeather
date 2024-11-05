package com.example.myweatherapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.data.Result
import com.example.myweatherapp.data.WeatherRepository
import com.example.myweatherapp.data.model.WeatherData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class TestWeatherVM(
    private val weatherRepository: WeatherRepository
): ViewModel() {

    //TEST METHODS REMOVE THIS
//    init {
//        collectTimeAsFlow()
//    }

    private val _weather = MutableStateFlow(WeatherData.EMPTY)
    val  weather = _weather.asStateFlow()

    private val _time = MutableStateFlow("")
    val time = _time.asStateFlow()

//    val timeAsFlow = flow<String> {
//        emit("")
//        while(true) {
//            delay(1000L)
////            val sdf = SimpleDateFormat("hh:mm:ss a", Locale.getDefault())
////            emit(sdf.format(Date()))
//            val formatter = DateTimeFormatter.ofPattern("hh:mm:ss a", Locale.getDefault())
//            val currentTime = LocalDateTime.now().format(formatter)
//            emit(currentTime)
//        }
//    }

    val timeAsFlow = flow<Int> {
        var currentTime = 10
        emit(currentTime)
        while (currentTime>0){
            delay(1000L)
            currentTime--
            emit(currentTime)
        }
    }

    private val _showErrorToastChannel= Channel<Boolean>()
    val showErrorToastChannel = _showErrorToastChannel.receiveAsFlow()

    private fun collectTimeAsFlow() {
        viewModelScope.launch {
            timeAsFlow.collect { time ->
                delay(1500L)
                println("The current time is $time")
            }
        }
    }


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