package com.example.nuntium.ui.appLevelComp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.nuntium.R
import kotlinx.coroutines.launch


@Composable
fun ScrollController(scrollState: LazyListState, showAfterIndex: Int, botOffset: Dp = 15.dp) {
    val colors = MaterialTheme.colors
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 0.dp, 15.dp, botOffset),
        contentAlignment = Alignment.BottomEnd
    ) {
        AnimatedVisibility(
            visible = scrollState.firstVisibleItemIndex >= showAfterIndex,
            enter = fadeIn(), exit = fadeOut()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_up),
                contentDescription = "up",
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(colors.surface)
                    .clickable {
                        coroutineScope.launch {
                            scrollState.animateScrollToItem(0)
                        }
                    }
                    .padding(10.dp),
                tint = colors.onSurface
            )
        }
    }
}