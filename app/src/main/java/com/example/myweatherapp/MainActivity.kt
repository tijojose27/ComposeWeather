package com.example.myweatherapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myweatherapp.data.RetrofitInstance
import com.example.myweatherapp.data.WeatherRepositoryImpl
import com.example.myweatherapp.data.model.Clouds
import com.example.myweatherapp.data.model.WeatherData
import com.example.myweatherapp.data.model.getWeatherIcon
import com.example.myweatherapp.ui.TestWeatherVM
import com.example.myweatherapp.ui.testDigitalClock
import com.example.myweatherapp.ui.testUIWeatherCard
import com.example.myweatherapp.ui.theme.LocalExtendedColors
import com.example.myweatherapp.ui.theme.MyWeatherAppTheme
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
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val context = LocalContext.current
//                    testViewModel.getError()
                    testViewModel.getWeatherAsStateFlow()
                    val weather = testViewModel.weather.collectAsState()
//                    testViewModel.timeAsFlow.collectAsState(1)
//                    val time = testViewModel.timeAsFlow.collectAsState("")

                    showWeatherTimeDisplay(weather)

//                    MySimpleScreen()
                }
            }
        }
    }


}

@Composable
fun MySimpleScreen() {
    // Check if the system is in dark mode
    val isDarkMode = isSystemInDarkTheme()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isDarkMode) Color.DarkGray else Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text("Here is ", color = if (isDarkMode) Color.White else Color.DarkGray)
    }
}

@Preview(showSystemUi = true, device = "spec:width=1280dp,height=800dp,dpi=240")
@Preview(
    name = "Dark Mode",
    showSystemUi = true,
    device = "spec:width=1280dp,height=800dp,dpi=240",
    uiMode = Configuration.UI_MODE_NIGHT_UNDEFINED
)
@Composable
fun PreviewShowWeatherTimeDisplay() {
    // Replace this with your actual sample data class or mock data
    val sampleWeather = remember { mutableStateOf(WeatherData.EMPTY) }

    // Call the composable to be previewed
//    showWeatherTimeDisplay(weather = sampleWeather)
    val mainColorGradient1 =
        Box(
            modifier = Modifier
                .fillMaxSize()
//            .background(
//                brush = Brush.linearGradient(
//                    0f to colors.mainColorGradient1,
//                    0.5f to colors.mainColorGradient2,
//                    1f to colors.mainColorGradient3
//                )
//            ),
//        contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Empty Weather",
            )
        }
}

@Composable
private fun showWeatherTimeDisplay(weather: State<WeatherData>) {
    val isDarkMode = isSystemInDarkTheme()
    val colors = LocalExtendedColors.current
    if (weather.value == WeatherData.EMPTY) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        0f to colors.mainColorGradient1,
                        0.5f to colors.mainColorGradient2,
                        1f to colors.mainColorGradient3
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Empty Weather",
            )
        }
    } else {
        WeatherScreen(weatherData = weather.value)
    }
}

@Composable
fun WeatherScreen(weatherData: WeatherData) {
    val isDarkMode = isSystemInDarkTheme()

    val MainColorGradient1 = if (isDarkMode) Color(110, 90, 133) else Color(177, 198, 229)
    val MainColorGradient2 = if (isDarkMode) Color(32, 28, 37) else Color(224, 228, 240)
    val MainColorGradient3 = if (isDarkMode) Color(15, 14, 18) else Color(141, 155, 204)

    Box(
        modifier = Modifier
            .padding()
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    0f to MainColorGradient1,
                    0.5f to MainColorGradient2,
                    1f to MainColorGradient3
                )
            )
    )
    {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            testUIWeatherCard(weatherData = weatherData, modifier = Modifier.weight(1f))
            testDigitalClock(modifier = Modifier.weight(2f))
        }
    }
}


@Composable
private fun weatherCard(weatherData: WeatherData) {
    Text(
        text = weatherData.weather[0].description,
        fontSize = 20.sp,
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 48.dp),
        textAlign = TextAlign.Center
    )
    Image(
        painter = painterResource(id = getWeatherIcon(weatherData.weather[0].icon)),
        contentDescription = null,
        modifier = Modifier
            .size(150.dp)
            .padding(top = 8.dp)
    )
    Text(
        text = "Mon Jun 17 | 10:08AM",
        fontSize = 19.sp,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        textAlign = TextAlign.Center
    )
    Text(
        text = "${weatherData.main.temp.roundToInt()}°C",
        fontSize = 63.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        textAlign = TextAlign.Center
    )
    Text(
        text = "H:${weatherData.main.temp_max.roundToInt()} L:${weatherData.main.temp_min}",
        fontSize = 16.sp,
        color = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun weatherDetails(weatherData: WeatherData) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .background(
                color = colorResource(id = R.color.purple_200),
                shape = RoundedCornerShape(25.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            WeatherDetailItem(
                icon = R.drawable.img_windy,
                value = "${weatherData.wind.speed.roundToInt()}",
                label = "Wind Speed"
            )
            WeatherDetailItem(
                icon = R.drawable.humidity,
                value = "${weatherData.main.humidity} %",
                label = "Humidity"
            )
        }
    }
}

@Composable
fun WeatherDetailItem(icon: Int, value: String, label: String) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(34.dp)
        )
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.white),
            textAlign = TextAlign.Center
        )
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.white),
            textAlign = TextAlign.Center
        )
    }
}