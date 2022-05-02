package com.example.nuntium.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nuntium.MainViewModel
import com.example.nuntium.constants.AppThemeMode

private val LightColorPalette = darkColors(
    primary = Blue500,
    onPrimary = Color.White,
    background = Color.White,
    onBackground = Gray700,
    surface = Blue200,
    onSurface = Gray300
)

private val DarkColorPalette = lightColors(
    primary = Blue500,
    onPrimary = Color.White,
    background = Blue700,
    onBackground = Color.White,
    surface = Gray300,
    onSurface = Color.White

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun NuntiumTheme(content: @Composable () -> Unit) {
    val mainViewModel: MainViewModel = hiltViewModel()
    val appThemeMode = mainViewModel.appThemeMode.collectAsState()
    val colors = if (appThemeMode.value is AppThemeMode.DarkMode) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}