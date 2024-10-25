package com.example.myweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.myweatherapp.Network.NetworkResponse
import com.example.myweatherapp.data.WeatherData
import com.example.myweatherapp.ui.theme.MyWeatherAppTheme
import kotlin.math.round
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val weatherVM = ViewModelProvider(this)[WeatherVM::class.java]
        enableEdgeToEdge()
        setContent {
            MyWeatherAppTheme {
                Scaffold(modifier = Modifier
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

                ) { innerPadding ->
                    Greeting(weatherVM = weatherVM, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Greeting(weatherVM: WeatherVM, modifier: Modifier) {
    val weatherResult = weatherVM.weatherResult.observeAsState()

    when(val result = weatherResult.value){
        is NetworkResponse.Error -> {
            Text(text = result.message)
        }
        NetworkResponse.Loading -> {
            Text("LOADING")
        }
        is NetworkResponse.Success -> {
//            Text("SEUCCESS the temp is ${result.data.main.temp} for COUNTRY ${result.data.sys.country} ")
            WeatherScreen(result.data)
        }

        null -> {}
    }
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
                    Text(text = weatherData.weather[0].description,
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 48.dp),
                        textAlign = TextAlign.Center)
                    Image(painter = painterResource(id = R.drawable.cloudy_sunny),
                        contentDescription = null,
                        modifier = Modifier.size(150.dp).padding(top = 8.dp))
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
                    Text(text = "H:27 L:18",
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
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            WeatherDetailItem(icon = R.drawable.rain, value = "22%", label = "Rain")
                            WeatherDetailItem(icon = R.drawable.wind, value = "22%", label = "Wind Speed")
                            WeatherDetailItem(icon = R.drawable.humidity, value = weatherData.main.humidity.toString(), label = "Humidity")
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