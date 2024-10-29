//package com.example.myweatherapp
//
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.myweatherapp.Network.NetworkResponse
//import com.example.myweatherapp.data.RetrofitInstance
//import com.example.myweatherapp.data.model.WeatherData
//import kotlinx.coroutines.launch
//
//class WeatherVM : ViewModel() {
//
//    private val weatherAPI = RetrofitInstance.weatherAPI
//    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherData>>()
//    val weatherResult: LiveData<NetworkResponse<WeatherData>> = _weatherResult
//
//    private var _weatherData = MutableLiveData<WeatherData>()
//    val weatherData: LiveData<WeatherData> = _weatherData
//
//    init {
//        val fullUrl = RetrofitInstance.getFullUrl(
//            endpoint = "weather",
//            queryParams = mapOf(
//                "q" to "London",
//                "appid" to "your_api_key"
//            )
//        )
//
//        Log.e("VM DEBUG GET URL", fullUrl)  // Output: https://api.openweathermap.org/data/2.5/weather?q=London&appid=your_api_key
//        getData()
//
//    }
//
//    //use : https://www.figma.com/design/LfxPlArXOlJ74YNfQwpz8s/SALY---3D-Illustration-Pack-(Community)?node-id=7-4&node-type=canvas
//    //use : https://www.figma.com/design/kCYEnx8j7LCxOiBcr2sjFM/Weatherly-3D-Icons---Demo-version-(Community)?node-id=0-10
//    //use : https://github.com/Mercandj/android-dev-challenge-compose-4
//    fun getWeatherIcon(iconName: String): Int {
//        return when(iconName) {
//            "01d.png", "01n.png" -> R.drawable.img_sun        //clear sky
//            "02d.png", "02n.png" -> R.drawable.img_cloudy //few clouds
//            "03d.png", "03n.png" -> R.drawable.img_cloudy       //scattered clouds
//            "04d.png", "04n.png" -> R.drawable.img_clouds       //broken clouds
//            "09d.png", "09n.png" -> R.drawable.img_sub_rain        //shower rain
//            "10d.png", "10n.png" -> R.drawable.img_rain         //rain
//            "11d.png", "11n.png" -> R.drawable.img_thunder         //this is thunder
//            "13d.png", "13n.png" -> R.drawable.img_snow        //snowy
//            "50d.png", "50n.png" -> R.drawable.img_windy        //mist
//            else -> R.drawable.img_moon_stars
//        }
//    }
//
//    fun getData(){
//        _weatherResult.value = NetworkResponse.Loading
//        viewModelScope.launch {
//            try {
//                val response = weatherAPI.getWeather()
//                if (response.isSuccessful){
//                    response.body()?.let { data ->
//                        Log.e("VM DEBUG", data.toString())
//                        _weatherResult.value = NetworkResponse.Success(data)
//                        _weatherData.value = data
//                    } ?: run {
//                        _weatherResult.value = NetworkResponse.Error("Empty response body")
//                    }
//                } else {
//                    Log.e("VMDEBUG", response.message())
//                    _weatherResult.value = NetworkResponse.Error("Failed to load data")
//                }
//            } catch (e: Exception){
//                _weatherResult.value = NetworkResponse.Error("Failed to load data")
//            }
//        }
//
//    }
//
////    fun getData() {
////        viewModelScope.launch {
////            try {
////                val response = weatherAPI.getWeather()
////                _weatherData = response
////            }
////        }
////    }
//}