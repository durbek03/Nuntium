package com.example.nuntium.ui.appLevelStates

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun rememberWindowInfo() : WindowInfo {
    val configuration = LocalConfiguration.current
    return WindowInfo(
        widthInfo = when {
            configuration.screenWidthDp < 600 -> WindowInfo.WindowType.Compat
            configuration.screenWidthDp < 840 -> WindowInfo.WindowType.Compat
            else -> WindowInfo.WindowType.Extended
        },
        heightInfo = when {
            configuration.screenHeightDp < 480 -> WindowInfo.WindowType.Compat
            configuration.screenHeightDp < 900 -> WindowInfo.WindowType.Compat
            else -> WindowInfo.WindowType.Extended
        },
        width = configuration.screenWidthDp.dp,
        height = configuration.screenHeightDp.dp
    )
}

data class WindowInfo(
    val widthInfo: WindowType,
    val heightInfo: WindowType,
    val width: Dp,
    val height: Dp
) {
    sealed class WindowType {
        object Compat : WindowType()
        object Medium : WindowType()
        object Extended : WindowType()
    }
}