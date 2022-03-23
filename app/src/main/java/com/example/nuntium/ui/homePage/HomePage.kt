package com.example.nuntium.ui.homePage

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nuntium.R
import com.ramcosta.composedestinations.annotation.Destination
import com.example.nuntium.constants.Constants
import com.example.nuntium.constants.isScrolledToTheEnd
import com.example.nuntium.ui.homePage.states.HomePageStates
import com.example.nuntium.ui.homePage.viewModels.HomeViewModel
import com.example.nuntium.ui.homePage.viewModels.RecommendedNewsViewModel
import com.example.nuntium.ui.homePage.viewModels.SearchViewModel
import com.example.nuntium.ui.homePage.viewModels.TopicNewsViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun HomePage() {
    val colors = MaterialTheme.colors
    //homeviewmodel
    val homeViewModel: HomeViewModel = hiltViewModel()
    val pageState = homeViewModel.homePageState.collectAsState()
    //recommendViewModel
    val recommendedNewsViewModel: RecommendedNewsViewModel = hiltViewModel()
    val recommendNews = recommendedNewsViewModel.recommendNews.collectAsState()
    //topicNewsViewModel
    val topicNewsViewModel: TopicNewsViewModel = hiltViewModel()
    val selectedTabItem = topicNewsViewModel.selectedTabItem.collectAsState()
    //searchViewModel
    val searchViewModel: SearchViewModel = hiltViewModel()
    //ui related
    val columnState = rememberLazyListState()
    val topicListState = rememberLazyListState()
    val topicNewsListState = rememberLazyListState()
    val verticalLastIndex = columnState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(selectedTabItem.value) {
        topicNewsListState.scrollToItem(0)
    }
    LaunchedEffect(key1 = verticalLastIndex, key2 = recommendNews.value) {
        verticalLastIndex ?: return@LaunchedEffect
        Log.d("HomePagTag", "HomePage: ${columnState.firstVisibleItemIndex}")
        recommendedNewsViewModel.scrollIndex.emit(verticalLastIndex)
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
            item {
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
                Crossfade(targetState = pageState.value) {
                    if (it is HomePageStates.CasualPage) {
                        CasualPage(topicState = topicListState, newsState = topicNewsListState)
                    }
                }
            }
            itemsIndexed(items = recommendNews.value) { index, item ->
                Crossfade(targetState = pageState.value) {
                    if (it is HomePageStates.CasualPage) {
                        RecItemStates(itemState = item, modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            AnimatedVisibility(
                visible = columnState.firstVisibleItemIndex >= 3,
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
                                columnState.animateScrollToItem(0)
                            }
                        }
                        .padding(10.dp),
                    tint = colors.onSurface
                )
            }
        }
    }
}

@Composable
fun CasualPage(modifier: Modifier = Modifier, topicState: LazyListState, newsState: LazyListState) {
    val topicList = Constants.TOPICS
    Column(modifier = modifier) {
        TopicsLazyRow(
            topics = topicList,
            modifier = Modifier.fillMaxWidth(),
            state = topicState
        )

        TopicNewsLazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            state = newsState
        )
        RecHeader(
            modifier = Modifier
                .padding(15.dp, 15.dp, 15.dp, 10.dp)
                .fillMaxWidth()
        )
    }
}