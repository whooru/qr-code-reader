package com.scanny.qrcodereader.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    background = Black,
    surface = DarkGrey,
    onBackground = White,
    onSurface = White,
    primary = DarkGrey,
    onPrimary = White,
    primaryVariant = White,
    secondary = DarkGrey,
    onSecondary = White
)

private val LightColorPalette = lightColors(
//    background = White,
//    onBackground = Black,
//    surface = LightGrey,
//    primary = Black,
//    secondary = LightGrey,
//    error = RedC,
    background = White,
    surface = LightGrey,
    onBackground = Black,
    onSurface = Black,
    primary = LightGrey,
    onPrimary = Black,
    primaryVariant = Black,
    secondary = LightGrey,
    onSecondary = Black

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
fun QrCodeReaderTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
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