package com.example.myweatherapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val AppColorTheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)


@Composable
fun MyWeatherAppTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = AppColorTheme,
        typography = Typography,
        content = content
    )
}