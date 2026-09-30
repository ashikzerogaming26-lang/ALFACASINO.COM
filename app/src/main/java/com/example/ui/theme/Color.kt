package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Premium Dark Gaming Palette
val DarkBg = Color(0xFF090A0F)
val DarkSurface = Color(0xFF11131C)
val DarkSurfaceElevated = Color(0xFF181B27)
val DarkCardBg = Color(0xFF1E2232)
val DarkCardBorder = Color(0xFF2B3248)

// Gold & Accent Highlights
val GoldPrimary = Color(0xFFFFC727)
val GoldSecondary = Color(0xFFFFE066)
val GoldDark = Color(0xFFD49A00)
val GoldGlow = Color(0x66FFC727)

val GoldGradient = Brush.horizontalGradient(
    colors = listOf(Color(0xFFFFDF00), Color(0xFFFFA500))
)
val GoldGradientVertical = Brush.verticalGradient(
    colors = listOf(Color(0xFFFFE57F), Color(0xFFFFB300), Color(0xFFE65100))
)
val CardShimmer = Brush.linearGradient(
    colors = listOf(Color(0xFF1E2232), Color(0xFF262C3F), Color(0xFF1E2232))
)

// Accent Colors
val AccentRed = Color(0xFFFF3366)
val AccentGreen = Color(0xFF00E676)
val AccentCyan = Color(0xFF00E5FF)
val AccentPurple = Color(0xFF9D4EDD)

// Text Colors
val TextWhite = Color(0xFFFFFFFF)
val TextLight = Color(0xFFE2E8F0)
val TextMuted = Color(0xFF8E9BAE)
val TextDim = Color(0xFF556075)

// Category Accent
val CategoryActiveBg = Color(0xFF23283B)
val CategoryPill = Color(0xFFFFC727)
