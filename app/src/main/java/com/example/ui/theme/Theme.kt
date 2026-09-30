package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkGamingColorScheme = darkColorScheme(
    primary = GoldPrimary,
    onPrimary = DarkBg,
    primaryContainer = GoldDark,
    onPrimaryContainer = TextWhite,
    secondary = GoldSecondary,
    onSecondary = DarkBg,
    secondaryContainer = DarkSurfaceElevated,
    onSecondaryContainer = GoldSecondary,
    background = DarkBg,
    onBackground = TextWhite,
    surface = DarkSurface,
    onSurface = TextWhite,
    surfaceVariant = DarkCardBg,
    onSurfaceVariant = TextMuted,
    outline = DarkCardBorder,
    outlineVariant = DarkSurfaceElevated,
    error = AccentRed,
    onError = TextWhite
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkGamingColorScheme,
        typography = Typography,
        content = content
    )
}
