package com.example.wishlistapp.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val presetColors = mapOf(
    "Pink"   to Color(0xFFFFC0CB),
    "Purple" to Color(0xFFD8B4FE),
    "Blue"   to Color(0xFFBAD7F2),
    "Green"  to Color(0xFFB5EAD7),
    "Orange" to Color(0xFFFFD7A8),
    "Red"    to Color(0xFFFFB3B3)
)

@Composable
fun WishlistAppTheme(
    selectedColor: String = "Pink",
    content: @Composable () -> Unit
) {
    val primary = presetColors[selectedColor] ?: Color(0xFFFFC0CB)
    
    val m3ColorScheme = lightColorScheme(
        primary = primary
    )

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = primary.toArgb()


            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = true
        }
    }

    androidx.compose.material.MaterialTheme(
        colors = androidx.compose.material.lightColors(primary = primary)
    ) {


    MaterialTheme(
        colorScheme = m3ColorScheme,
        typography = Typography,
        content = content
    )
}}