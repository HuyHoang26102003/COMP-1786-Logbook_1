package com.example.logbook1.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFFF6F61),
    secondary = Color(0xFF6B5B95),
    background = Color(0xFFF5F5F5),
    onBackground = Color(0xFF212121),
    error = Color(0xFFB00020)
)

@Composable
fun Logbook1Theme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}