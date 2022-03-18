package com.example.nuntium.ui.homePage

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@Composable
fun TopicsLazyRow(modifier: Modifier = Modifier, topics: List<String>) {
    val viewModel: HomeViewModel = hiltViewModel()
    val selectedTabItem = viewModel.selectedTabItem.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val lazyRowState = rememberLazyListState()
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        state = lazyRowState,
        contentPadding = PaddingValues(5.dp, 0.dp, 5.dp, 5.dp)
    ) {
        itemsIndexed(items = topics) { index, topic ->
            val boxColor =
                animateColorAsState(targetValue = if (selectedTabItem.value == index) MaterialTheme.colors.primary else MaterialTheme.colors.surface)
            val textColor =
                animateColorAsState(targetValue = if (selectedTabItem.value == index) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface)
            Box(
                modifier = Modifier
                    .padding(5.dp, 0.dp, 5.dp, 5.dp)
                    .defaultMinSize(minWidth = 100.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(boxColor.value)
                    .clickable {
                        coroutineScope.launch {
                            viewModel.selectedTabItem.emit(index)
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = topic,
                    modifier = Modifier.padding(20.dp, 10.dp),
                    color = textColor.value,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
