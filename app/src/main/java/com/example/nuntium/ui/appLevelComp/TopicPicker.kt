package com.example.nuntium.ui.appLevelComp

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nuntium.ui.homePage.viewModels.RecommendedNewsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TopicPicker(
    modifier: Modifier = Modifier,
    topicList: List<String>,
    pickedTopics: SnapshotStateList<String>,
    onTopicPicked: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Box(modifier = modifier) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            cells = GridCells.Fixed(2)
        ) {
            itemsIndexed(topicList) { _, topic ->
                TopicItem(
                    modifier = Modifier
                        .padding(10.dp, 10.dp)
                        .fillMaxWidth()
                        .height(55.dp),
                    topic = topic,
                    isPicked = pickedTopics.contains(topic)
                ) {
                    coroutineScope.launch {
                        if (pickedTopics.contains(topic)) pickedTopics.remove(topic) else pickedTopics.add(topic)
                        Log.d("TopicPicked", "TopicPicker: newTopic")
                        var str = ""
                        for (i in pickedTopics) {
                            str += "$i "
                        }
                        onTopicPicked.invoke(str)
                    }
                }
            }
        }
    }
}

@Composable
fun TopicItem(
    modifier: Modifier = Modifier,
    topic: String,
    isPicked: Boolean,
    onClick: () -> Unit
) {
    val boxColorAnimation =
        animateColorAsState(targetValue = if (isPicked) MaterialTheme.colors.primary else MaterialTheme.colors.surface)

    val textColorAnimation =
        animateColorAsState(targetValue = if (isPicked) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface)
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(15.dp))
            .background(color = boxColorAnimation.value)
            .clickable { onClick.invoke() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = topic, color = textColorAnimation.value)
    }
}