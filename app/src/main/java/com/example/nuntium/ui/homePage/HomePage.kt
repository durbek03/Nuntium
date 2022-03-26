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
import com.example.nuntium.ui.appLevelStates.WindowInfo
import com.example.nuntium.ui.appLevelStates.rememberWindowInfo
import com.example.nuntium.ui.homePage.intent.HomePageIntent
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
    val homePageState = homeViewModel.homePageState.collectAsState()
    val casualVerticalScrollState = rememberLazyListState()
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
        recommendedNewsViewModel.scrollIndex.emit(verticalLastIndex)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colors.background)
    ) {
        Crossfade(targetState = homePageState.value) {
            when (it) {
                HomePageStates.CasualPage -> {
                    CasualPage(
                        verticalScrollState = casualVerticalScrollState,
                        topicListState = topicListState,
                        topicNewsListState = topicNewsListState
                    )
                }
                HomePageStates.SearchOn -> {
                    SearchPage(modifier = Modifier, columnState = rememberLazyListState())
                }
            }
        }
    }
}