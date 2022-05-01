package com.example.nuntium.ui.homePage

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nuntium.constants.Constants
import com.example.nuntium.ui.appLevelComp.ScrollController
import com.example.nuntium.ui.destinations.SearchPageDestination
import com.example.nuntium.ui.homePage.intent.HomePageIntent
import com.example.nuntium.ui.homePage.viewModels.HomeViewModel
import com.example.nuntium.ui.homePage.viewModels.RecommendedNewsViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch

@Composable
fun CasualPage(
    modifier: Modifier = Modifier,
    verticalScrollState: LazyListState,
    topicListState: LazyListState,
    topicNewsListState: LazyListState,
    navigator: DestinationsNavigator
) {
    //viewModels
    val homeViewModel: HomeViewModel = hiltViewModel()
    val recommendedNewsViewModel: RecommendedNewsViewModel = hiltViewModel()
    val recommendNews = recommendedNewsViewModel.recommendNews.collectAsState()
    //remembers
    val coroutineScope = rememberCoroutineScope()
    //uiRelated
    val topicList = Constants.TOPICS
    val colors = MaterialTheme.colors

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = verticalScrollState,
        contentPadding = PaddingValues(0.dp, 0.dp, 0.dp, 50.dp)
    ) {
        item {
            HeaderText()
        }
        item {
            SearchBar(
                "",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colors.background)
                    .padding(15.dp, 10.dp)
                    .height(55.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(color = MaterialTheme.colors.surface),
                onFocusChanged = {
                    if (it) {
                        Log.d("Focus change", "CasualPage: navigating")
                        coroutineScope.launch {
                            homeViewModel.action.emit(HomePageIntent.OpenSearchPage)
                        }
                        navigator.navigate(SearchPageDestination)
                    }
                },
                leftIconClicked = {},
                onTextChange = {},
                focusOnLaunch = false
            )
        }
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                TopicsLazyRow(
                    topics = topicList,
                    modifier = Modifier.fillMaxWidth(),
                    state = topicListState
                )

                TopicNewsLazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    state = topicNewsListState,
                    navigator = navigator
                )
                RecHeader(
                    modifier = Modifier
                        .padding(15.dp, 15.dp, 15.dp, 10.dp)
                        .fillMaxWidth()
                )
            }
        }
        itemsIndexed(items = recommendNews.value) { index, item ->
            RecItemStates(itemState = item, modifier = Modifier.fillMaxWidth())
        }
    }
    ScrollController(scrollState = verticalScrollState, 3, 50.dp)
}