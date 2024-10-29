package com.example.myweatherapp.data.model

import android.util.Log
import com.example.myweatherapp.R

data class WeatherData(
    val base: String = "",
    val clouds: Clouds = Clouds(),
    val cod: Int = 0,
    val coord: Coord = Coord(),
    val dt: Int = 0,
    val id: Int = 0,
    val main: Main = Main(),
    val name: String = "",
    val sys: Sys = Sys(),
    val timezone: Int = 0,
    val visibility: Int = 0,
    val weather: List<Weather> = listOf(Weather()),
    val wind: Wind = Wind()
) {
    companion object {
        val EMPTY = WeatherData()
    }
}

data class Clouds(val all: Int = 0)
data class Coord(val lat: Double = 0.0, val lon: Double = 0.0)
data class Main(
    val feels_like: Double = 0.0,
    val grnd_level: Int = 0,
    val humidity: Int = 0,
    val pressure: Int = 0,
    val sea_level: Int = 0,
    val temp: Double = 0.0,
    val temp_max: Double = 0.0,
    val temp_min: Double = 0.0
)
data class Sys(
    val country: String = "",
    val id: Int = 0,
    val sunrise: Int = 0,
    val sunset: Int = 0,
    val type: Int = 0
)
data class Weather(
    val description: String = "",
    val icon: String = "",
    val id: Int = 0,
    val main: String = ""
)
data class Wind(val deg: Int = 0, val speed: Double = 0.0)


fun getWeatherIcon(iconName: String): Int {
    Log.e("ICON", iconName)
    return when(iconName) {
        "01d", "01n" -> R.drawable.img_sun        //clear sky
        "02d", "02n" -> R.drawable.img_cloudy //few clouds
        "03d", "03n" -> R.drawable.img_cloudy       //scattered clouds
        "04d", "04n" -> R.drawable.img_clouds       //broken clouds
        "09d", "09n" -> R.drawable.img_sub_rain        //shower rain
        "10d", "10n" -> R.drawable.img_rain         //rain
        "11d", "11n" -> R.drawable.img_thunder         //this is thunder
        "13d", "13n" -> R.drawable.img_snow        //snowy
        "50d", "50n" -> R.drawable.img_windy        //mist
        else -> R.drawable.img_moon_stars
    }
}