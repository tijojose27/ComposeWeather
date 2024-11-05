package com.example.myweatherapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.content.contentReceiver
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.myweatherapp.data.model.WeatherData
import com.example.myweatherapp.data.model.getWeatherIcon
import com.example.myweatherapp.ui.theme.ColorGradient1
import com.example.myweatherapp.ui.theme.ColorGradient2
import com.example.myweatherapp.ui.theme.ColorGradient3
import com.example.myweatherapp.ui.theme.ColorTextSecondary
import com.example.myweatherapp.ui.theme.ColorTextSecondaryVariant
import com.example.myweatherapp.ui.theme.timeColorGradient1
import com.example.myweatherapp.ui.theme.timeColorGradient2
import com.example.myweatherapp.ui.theme.timeColorGradient3


@Composable
fun testUIWeatherCard(weatherData: WeatherData, modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 50.dp, start = 10.dp, end = 10.dp)

    ) {
        val (weatherIcon, forecastValue, windoImage, title, description, background) = createRefs()

        CardBackground(
            modifier = Modifier.constrainAs(background) {
                linkTo(
                    start = parent.start,
                    end = parent.end,
                    top = parent.top,
                    bottom = description.bottom,
                    topMargin = 60.dp
                )
                height = Dimension.fillToConstraints
            }
        )

        Image(
            painter = painterResource(id = getWeatherIcon(weatherData.weather[0].icon)),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .height(175.dp)
                .constrainAs(weatherIcon) {
                    start.linkTo(anchor = parent.start, margin = 4.dp)
                    top.linkTo(anchor = parent.top)
                }
        )

        Text(
            text = weatherData.main.temp.toString(),
            style = MaterialTheme.typography.titleLarge,
            color = ColorTextSecondary,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.constrainAs(title) {
                start.linkTo(anchor = parent.start, margin = 24.dp)
                top.linkTo(anchor = weatherIcon.bottom)
            }
        )

        Text(
            text = weatherData.main.feels_like.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = ColorTextSecondaryVariant,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .constrainAs(description) {
                    start.linkTo(anchor = title.start, margin = 24.dp)
                    top.linkTo(anchor = title.bottom)
                }
                .padding(bottom = 24.dp)
        )

        ForecastValue(
            modifier = Modifier.constrainAs(forecastValue) {
                end.linkTo(anchor = parent.end, margin = 24.dp)
                top.linkTo(anchor = weatherIcon.top)
                bottom.linkTo(anchor = weatherIcon.bottom)
            }
        )

    }
}

@Composable
private fun CardBackground(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.linearGradient(
                    0f to ColorGradient1,
                    0.5f to ColorGradient2,
                    1f to ColorGradient3
                ),
                shape = RoundedCornerShape(32.dp)
            )
    )
}

@Composable
private fun ForecastValue(
    modifier: Modifier = Modifier,
    degree: String = "21",
    description: String = "Feel like 26"
) {
    Column(
        modifier = modifier.padding(top = 40.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Box(contentAlignment = Alignment.TopEnd) {
            Text(
                text = degree, style = TextStyle(
                    brush = Brush.linearGradient(
                        0f to Color.White,
                        1f to Color.White.copy(alpha = 0.3f)
                    ),
                    fontSize = 80.sp,
                    fontWeight = FontWeight.Black
                ),
                modifier = Modifier.padding(end = 16.dp)
            )
            Text(
                text = "°", style = TextStyle(
                    brush = Brush.linearGradient(
                        0f to Color.White,
                        1f to Color.White.copy(alpha = 0.3f)
                    ),
                    fontSize = 70.sp,
                    fontWeight = FontWeight.Light
                )
            )
        }
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            color = ColorTextSecondaryVariant
        )
    }
}


//Image(
//painter = painterResource(id = R.drawable.img_clouds),
//contentDescription = null,
//contentScale = ContentScale.Fit,
//modifier = Modifier
//.fillMaxWidth()
//.offset(y = (-50).dp)
//)

//    Text(
//        text = weatherData.weather[0].description,
//        fontSize = 20.sp,
//        color = Color.White,
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(top = 48.dp),
//        textAlign = TextAlign.Center
//    )
//    Image(
//        painter = painterResource(id = getWeatherIcon(weatherData.weather[0].icon)),
//        contentDescription = null,
//        modifier = Modifier
//            .size(150.dp)
//            .padding(top = 8.dp)
//    )
//    Text(
//        text = "Mon Jun 17 | 10:08AM",
//        fontSize = 19.sp,
//        color = Color.White,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(top = 8.dp),
//        textAlign = TextAlign.Center
//    )
//    Text(
//        text = "${weatherData.main.temp.roundToInt()}°C",
//        fontSize = 63.sp,
//        fontWeight = FontWeight.Bold,
//        color = Color.White,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(top = 8.dp),
//        textAlign = TextAlign.Center
//    )
//    Text(
//        text = "H:${weatherData.main.temp_max.roundToInt()} L:${weatherData.main.temp_min}",
//        fontSize = 16.sp,
//        color = Color.White,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(top = 8.dp),
//        textAlign = TextAlign.Center
//    )


@Preview(showSystemUi = true, device = "id:pixel_5")
@Composable
fun WeatherPreview() {
    testUIWeatherCard(weatherData = WeatherData.EMPTY)
}