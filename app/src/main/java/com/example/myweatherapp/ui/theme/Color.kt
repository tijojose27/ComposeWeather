package com.example.myweatherapp.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val ColorGradient1 = Color(0xFFe262e6)
val ColorGradient2 = Color(0xFF9f62ea)
val ColorGradient3 = Color(0xFF5264f0)

//val mainColorGradient1 = Color(110, 90, 133)
//val mainColorGradient2 = Color(32, 28, 37)
//val mainColorGradient3 = Color(15, 14, 18)

val ColorBackground = Color(0xFFF4DCF7).copy(alpha = 0.4f)
val ColorSurface = Color.White
val ColorImageShadow = Color(0xFFfdc228)
val ColorWindForecast = Color.White.copy(alpha = 0.2f)
val ColorAirQualityIconTitle = Color(0xFFa09bf0)

val ColorTextPrimary = Color(0xFF2c2e35)
val ColorTextPrimaryVariant = ColorTextPrimary.copy(alpha = 0.7f)
val ColorTextSecondary = Color.White
val ColorTextSecondaryVariant = ColorTextSecondary.copy(alpha = 0.7f)
val ColorTextAction = ColorGradient2


val MainLightColorGradient1 = Color(110, 90, 133)
val MainLightColorGradient2 = Color(32, 28, 37)
val MainLightColorGradient3 = Color(15, 14, 18)

val MainDarkColorGradient1 = Color(110, 90, 133)
val MainDarkColorGradient2 = Color(32, 28, 37)
val MainDarkColorGradient3 = Color(15, 14, 18)


data class ExtendedColorScheme(
    val mainColorGradient1: Color,
    val mainColorGradient2: Color,
    val mainColorGradient3: Color
)
