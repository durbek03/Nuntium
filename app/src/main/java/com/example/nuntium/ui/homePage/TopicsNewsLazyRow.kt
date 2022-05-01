package com.example.nuntium.ui.homePage

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Colors
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.nuntium.data.locale.News
import com.example.nuntium.R
import com.example.nuntium.constants.isScrolledToTheEnd
import com.example.nuntium.ui.appLevelComp.customBrush
import com.example.nuntium.ui.appLevelStates.ListItemState
import com.example.nuntium.ui.destinations.NewsDetailedScreenDestination
import com.example.nuntium.ui.homePage.viewModels.HomeViewModel
import com.example.nuntium.ui.homePage.viewModels.TopicNewsViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@OptIn(ExperimentalCoilApi::class)
@Composable
fun TopicNewsLazyRow(
    modifier: Modifier = Modifier,
    state: LazyListState,
    navigator: DestinationsNavigator
) {
    val topicNewsViewModel: TopicNewsViewModel = hiltViewModel()
    val topicNews = topicNewsViewModel.topicNews.collectAsState()
    val lazyRowState = state
    val lastIndex = lazyRowState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
    LaunchedEffect(key1 = lastIndex, topicNews.value) {
        lastIndex ?: return@LaunchedEffect
        topicNewsViewModel.lastVisibleIndex.emit(lastIndex)
    }
    LazyRow(
        state = lazyRowState,
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        contentPadding = PaddingValues(5.dp, 0.dp)
    ) {
        itemsIndexed(items = topicNews.value) { index: Int, item: ListItemState<News> ->
            Crossfade(targetState = item) { state ->
                when (state) {
                    is ListItemState.LoadedItemState -> {
                        TopicNewsItem(
                            modifier = Modifier
                                .padding(5.dp, 0.dp)
                                .fillMaxHeight()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(15.dp))
                                .clickable { navigator.navigate(NewsDetailedScreenDestination(state.item)) },
                            state = state
                        )
                    }
                    is ListItemState.LoadingItemState -> {
                        val customBrush = customBrush()
                        Box(
                            modifier = modifier
                                .padding(5.dp, 0.dp)
                                .fillMaxHeight()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(15.dp))
                                .background(brush = customBrush)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TopicNewsItem(modifier: Modifier = Modifier, state: ListItemState<News>) {
    Box(
        modifier = modifier,
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(15.dp)),
            painter = rememberImagePainter(state.data?.image),
            contentDescription = "News Image",
            contentScale = ContentScale.FillBounds
        )
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f)
                    .background(color = colorResource(id = R.color.semiTransparent))
                    .padding(15.dp, 0.dp),
                text = state.data?.title ?: "not found",
                fontSize = 17.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 3
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 15.dp, 15.dp, 0.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_save),
                contentDescription = "Save",
                tint = Color.White,
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(color = colorResource(id = R.color.semiTransparent))
                    .padding(5.dp)
            )
        }
    }
}