package com.example.nuntium.ui.homePage

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.example.nuntium.constants.Constants
import com.example.nuntium.constants.isScrolledToTheEnd
import com.example.nuntium.ui.homePage.states.HomePageStates

@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun HomePage() {
    val colors = MaterialTheme.colors
    val viewModel: HomeViewModel = hiltViewModel()
    val recommendedNewsViewModel: RecommendedNewsViewModel = hiltViewModel()
    val recommendNews = recommendedNewsViewModel.recommendNews.collectAsState()
    val columnState = rememberLazyListState()
    LaunchedEffect(key1 = columnState.firstVisibleItemScrollOffset, key2 = recommendNews.value) {
        recommendedNewsViewModel.isScrolledToEnd.emit(columnState.isScrolledToTheEnd())
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colors.background)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = columnState
        ) {
            item {
                Text(
                    modifier = Modifier.padding(15.dp, 15.dp, 15.dp, 0.dp),
                    text = "Browse",
                    color = colors.onBackground,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.padding(15.dp, 0.dp),
                    text = "Discover things of this world",
                    color = colors.onSurface,
                    fontSize = 17.sp
                )
            }
            stickyHeader {
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = colors.background)
                        .padding(15.dp, 10.dp)
                        .height(55.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(color = MaterialTheme.colors.surface)
                )
            }
            item {
                Crossfade(targetState = viewModel.pageState.value) {
                    if (it is HomePageStates.CasualPage) {
                        CasualPage()
                    }
                }
            }
            item {
                Crossfade(targetState = viewModel.pageState.value) {
                    if (it is HomePageStates.CasualPage) {
                        RecHeader(
                            modifier = Modifier
                                .padding(15.dp, 15.dp, 15.dp, 10.dp)
                                .fillMaxWidth()
                        )
                    }
                }
            }
            itemsIndexed(items = recommendNews.value) { index, item ->
                Crossfade(targetState = viewModel.pageState.value) {
                    if (it is HomePageStates.CasualPage) {
                        RecItemStates(itemState = item, modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
    }
}

@Composable
fun CasualPage(modifier: Modifier = Modifier) {
    val topicList = Constants.TOPICS
    Column(modifier = modifier) {
        TopicsLazyRow(
            topics = topicList,
            modifier = Modifier.fillMaxWidth()
        )

        TopicNewsLazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
    }
}