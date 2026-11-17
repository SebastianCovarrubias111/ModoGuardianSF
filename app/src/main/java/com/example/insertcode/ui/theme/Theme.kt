package com.example.insertcode.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val GuardianDarkScheme = darkColorScheme(
    primary = GdPrimary,
    onPrimary = Color.White,
    secondary = GdInfo,
    tertiary = GdSuccess,
    background = GdBackground,
    onBackground = GdTextPrimary,
    surface = GdSurface,
    onSurface = GdTextPrimary,
    surfaceVariant = GdSurfaceHigh,
    onSurfaceVariant = GdTextMuted,
    outline = GdBorder,
    outlineVariant = GdBorder,
    error = GdDanger
)

@Composable
fun InsertCodeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = GuardianDarkScheme,
        typography = Typography,
        content = content
    )
}