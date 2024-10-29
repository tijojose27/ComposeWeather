package com.example.myweatherapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myweatherapp.data.RetrofitInstance
import com.example.myweatherapp.data.WeatherRepositoryImpl
import com.example.myweatherapp.data.model.WeatherData
import com.example.myweatherapp.data.model.getWeatherIcon
import com.example.myweatherapp.ui.TestWeatherVM
import com.example.myweatherapp.ui.theme.MyWeatherAppTheme
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt


class MainActivity : ComponentActivity() {
    private val testViewModel by viewModels<TestWeatherVM>(factoryProducer = {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TestWeatherVM(WeatherRepositoryImpl(RetrofitInstance.weatherAPI))
                as T
            }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyWeatherAppTheme {
                Surface(modifier = Modifier
                    .fillMaxSize()
//                    .pointerInput(Unit) {
//                        detectTapGestures (
//                            onTap = {
//                                finish()
//                            }
//                        )
//                    }
//                ) { innerPadding ->
//                    Greeting(
//                        weatherVM,
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }

                ) {

                    val weather = testViewModel.weather.collectAsState().value
                    val context = LocalContext.current

                    LaunchedEffect(key1 = testViewModel.showErrorToastChannel) {
                        testViewModel.showErrorToastChannel.collectLatest { show ->
                            if (show) {
                                Toast.makeText(
                                    context, "Error", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    if (weather.name.isEmpty()) {

                    } else {
                        WeatherScreen(weatherData = weather)
                    }
                }
            }
        }
    }
}


@Composable
fun DigitalClock() {
    var currentTime by remember { mutableStateOf(getCurrentTime()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime = getCurrentTime()
            kotlinx.coroutines.delay(1000L)
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = currentTime,
            fontSize = 48.sp
        )
    }
}

fun getCurrentTime(): String {
    val sdf = SimpleDateFormat("hh:mm:ss a", Locale.getDefault())
    return sdf.format(Date())
}

@Composable
fun WeatherScreen(weatherData: WeatherData) {
    Box(modifier = Modifier
        .padding()
        .fillMaxSize()
        .background(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color(android.graphics.Color.parseColor("#59469d")),
                    Color(android.graphics.Color.parseColor("#643d67")),
                )
            )
        ))
    {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                item {
                    DigitalClock()
                    Text(text = weatherData.weather[0].description,
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 48.dp),
                        textAlign = TextAlign.Center)
                    Image(painter = painterResource(id = getWeatherIcon(weatherData.weather[0].icon)),
                        contentDescription = null,
                        modifier = Modifier
                            .size(150.dp)
                            .padding(top = 8.dp))
                    Text(text = "Mon Jun 17 | 10:08AM",
                        fontSize = 19.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        textAlign = TextAlign.Center
                        )
                    Text(text = "${weatherData.main.temp.roundToInt()}°C",
                        fontSize = 63.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        textAlign = TextAlign.Center
                        )
                    Text(text = "H:${weatherData.main.temp_max.roundToInt()} L:${weatherData.main.temp_min}",
                        fontSize = 16.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        textAlign = TextAlign.Center
                    )

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                        .background(
                            color = colorResource(id = R.color.purple_200),
                            shape = RoundedCornerShape(25.dp)
                        )
                    ) {
                        Row (modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            WeatherDetailItem(icon = R.drawable.img_windy, value = "${weatherData.wind.speed.roundToInt()}", label = "Wind Speed")
                            WeatherDetailItem(icon = R.drawable.humidity, value = "${weatherData.main.humidity} %", label = "Humidity")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherDetailItem(icon: Int, value: String, label: String) {
    Column(modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(34.dp)
            )
        Text(text = value,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.white),
            textAlign = TextAlign.Center
        )
        Text(text = label,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.white),
            textAlign = TextAlign.Center
        )
    }
}