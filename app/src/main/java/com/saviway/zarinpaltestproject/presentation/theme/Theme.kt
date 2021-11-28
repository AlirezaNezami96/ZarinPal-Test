package com.saviway.zarinpaltestproject.presentation.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@SuppressLint("ConflictingOnColor")
private val LightThemeColors = lightColors(
    primary = WHITE,
    primaryVariant = GREEN,
    onPrimary = Color.White,
    secondary = GREY,
    onSecondary = Color.Black,
    error = RedErrorLight,
    background = Color.Black,
    onBackground = Color.White,
    surface = BLACK_1,
    onSurface = Color.White,
)

private val DarkThemeColors = darkColors(
    primary = WHITE,
    primaryVariant = GREEN,
    onPrimary = Color.White,
    secondary = GREY,
    onSecondary = Color.Black,
    error = RedErrorLight,
    background = Color.Black,
    onBackground = Color.White,
    surface = BLACK_1,
    onSurface = Color.White,
)

@Composable
fun MyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkThemeColors
    } else {
        LightThemeColors
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}