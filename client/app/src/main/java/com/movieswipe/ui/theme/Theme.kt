package com.movieswipe.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@Composable
fun MovieSwipeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = lightColors(),
        content = content
    )
}
