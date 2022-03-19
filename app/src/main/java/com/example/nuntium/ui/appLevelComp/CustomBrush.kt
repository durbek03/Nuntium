package com.example.nuntium.ui.appLevelComp

import androidx.compose.animation.core.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun customBrush(): Brush {
    val colors = listOf<Color>(
        MaterialTheme.colors.onSurface.copy(alpha = 0.35f),
        MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
        MaterialTheme.colors.onSurface.copy(alpha = 0.35f)
    )
    val transition = rememberInfiniteTransition()
    val translate = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutLinearInEasing)
        )
    )
    return Brush.linearGradient(
        colors, start = Offset.Zero, end = Offset(x = translate.value, y = translate.value)
    )
}