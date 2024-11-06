package com.example.myweatherapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColorScheme(
        mainColorGradient1 = Color.Unspecified,
        mainColorGradient2 = Color.Unspecified,
        mainColorGradient3 = Color.Unspecified
    )
}

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80

)

private val LightColorScheme = lightColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)


@Composable
fun MyWeatherAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }


    val extendedColors = if (!darkTheme) {
        ExtendedColorScheme(
            mainColorGradient1 = MainLightColorGradient1,
            mainColorGradient2 = MainLightColorGradient2,
            mainColorGradient3 = MainLightColorGradient3
        )
    } else {
        ExtendedColorScheme(
            mainColorGradient1 = MainDarkColorGradient1,
            mainColorGradient2 = MainDarkColorGradient2,
            mainColorGradient3 = MainDarkColorGradient3
        )
    }

    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}