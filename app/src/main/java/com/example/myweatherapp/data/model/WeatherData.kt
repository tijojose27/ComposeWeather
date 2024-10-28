package com.example.myweatherapp.data

import com.example.myweatherapp.R

data class WeatherData(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)

data class Clouds(
    val all: Int
)

data class Coord(
    val lat: Double,
    val lon: Double
)

data class Main(
    val feels_like: Double,
    val grnd_level: Int,
    val humidity: Int,
    val pressure: Int,
    val sea_level: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
)

data class Sys(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

data class Wind(
    val deg: Int,
    val speed: Double
)

fun getWeatherIcon(iconName: String): Int {
    return when(iconName) {
        "01d.png", "01n.png" -> R.drawable.img_sun        //clear sky
        "02d.png", "02n.png" -> R.drawable.img_cloudy //few clouds
        "03d.png", "03n.png" -> R.drawable.img_cloudy       //scattered clouds
        "04d.png", "04n.png" -> R.drawable.img_clouds       //broken clouds
        "09d.png", "09n.png" -> R.drawable.img_sub_rain        //shower rain
        "10d.png", "10n.png" -> R.drawable.img_rain         //rain
        "11d.png", "11n.png" -> R.drawable.img_thunder         //this is thunder
        "13d.png", "13n.png" -> R.drawable.img_snow        //snowy
        "50d.png", "50n.png" -> R.drawable.img_windy        //mist
        else -> R.drawable.img_moon_stars
    }
}