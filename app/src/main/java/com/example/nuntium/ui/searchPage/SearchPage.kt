package com.example.nuntium.ui.searchPage

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.nuntium.MainViewModel
import com.example.nuntium.data.locale.News
import com.example.nuntium.ui.appLevelComp.customBrush
import com.example.nuntium.ui.appLevelStates.ListItemState
import com.example.nuntium.ui.appLevelStates.rememberWindowInfo
import com.example.nuntium.ui.homePage.viewModels.SearchViewModel
import kotlinx.coroutines.launch
import com.example.nuntium.R
import com.example.nuntium.ui.appLevelComp.ScrollController
import com.example.nuntium.ui.destinations.NewsDetailedScreenDestination
import com.example.nuntium.ui.detailedNewsPage.DetailedNewsViewModel
import com.example.nuntium.ui.homePage.HeaderText
import com.example.nuntium.ui.homePage.SearchBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers

@Composable
@Destination
fun SearchPage(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    columnState: LazyListState = rememberLazyListState(),
    rowState: LazyListState = rememberLazyListState()
) {
    Log.d("SearchPage", "SearchPage: Search Page")
    //viewModel
    val searchViewModel: SearchViewModel = hiltViewModel()
    val mainViewModel: MainViewModel = hiltViewModel()
    //remembers
    val coroutineScope = rememberCoroutineScope()
    val searchResponse = searchViewModel.response.collectAsState()
    val scrollIndex = columnState.layoutInfo.visibleItemsInfo.lastOrNull()?.index

    //ui
    val colors = MaterialTheme.colors
    val windowInfo = rememberWindowInfo()

    LaunchedEffect(key1 = scrollIndex, key2 = searchResponse.value) {
        if (scrollIndex != null) {
            searchViewModel.scrollIndex.emit(scrollIndex)
        }
    }

    LaunchedEffect(key1 = true) {
        mainViewModel.canBackPress.emit(false)
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = columnState,
    ) {
        item { HeaderText() }
        item {
            SearchBar(
                searchViewModel.query.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colors.background)
                    .padding(15.dp, 10.dp)
                    .height(55.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(color = MaterialTheme.colors.surface),
                onFocusChanged = {},
                leftIconClicked = {
                    navigator.popBackStack()
                },
                onTextChange = { searchText ->
                    coroutineScope.launch {
                        searchViewModel.query.emit(searchText)
                    }
                },
                focusOnLaunch = true,
                icon = R.drawable.ic_cancel
            )
        }
        itemsIndexed(searchResponse.value) { index, item ->
            Crossfade(targetState = windowInfo.height) {
                if (it * 1.8f >= windowInfo.width) {
                    SearchItemMedium(
                        item = item,
                        modifier = Modifier.padding(15.dp, 5.dp),
                        navigator = navigator
                    )
                }
            }
        }
        item {
            Crossfade(targetState = windowInfo.height) {
                if (it * 1.8f < windowInfo.width) {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        state = rowState,
                        contentPadding = PaddingValues(10.dp, 0.dp),
                    ) {
                        itemsIndexed(searchResponse.value) { index, item ->
                            SearchItemExtended(
                                item = item,
                                modifier = Modifier.padding(5.dp),
                                navigator = navigator
                            )
                        }
                    }
                }
            }
        }
    }
    ScrollController(scrollState = columnState, showAfterIndex = 3)
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun SearchItemMedium(
    modifier: Modifier = Modifier,
    item: ListItemState<News>,
    navigator: DestinationsNavigator
) {
    val colors = MaterialTheme.colors
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(2 / 1f)
            .clip(RoundedCornerShape(15.dp))
            .background(color = colors.surface)
    ) {
        SearchItem(item = item, navigator = navigator)
    }
}

@Composable
fun SearchItemExtended(
    modifier: Modifier = Modifier,
    item: ListItemState<News>,
    navigator: DestinationsNavigator
) {
    val windowInfo = rememberWindowInfo()
    val colors = MaterialTheme.colors
    Box(
        modifier = modifier
            .width(windowInfo.width / 3.5f)
            .aspectRatio(2 / 1.5f)
            .clip(RoundedCornerShape(15.dp))
            .background(color = colors.surface)
    ) {
        SearchItem(item = item, navigator = navigator)
    }
}

@Composable
fun SearchItem(item: ListItemState<News>, navigator: DestinationsNavigator) {
    val mainViewModel: MainViewModel = hiltViewModel()
    val detailedNewsViewModel: DetailedNewsViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()
    val savedNews = mainViewModel.savedNews.collectAsState()
    val brush = customBrush()
    val colors = MaterialTheme.colors
    Crossfade(targetState = item) {
        when (it) {
            is ListItemState.LoadedItemState -> {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            navigator.navigate(NewsDetailedScreenDestination(news = item.data!!))
                        },
                    painter = rememberImagePainter(item.data?.image ?: ""),
                    contentDescription = "image",
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = colors.surface)
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = item.data?.title ?: "",
                            maxLines = 2,
                            color = colors.onBackground,
                            modifier = Modifier.fillMaxWidth(0.7f)
                        )
                        Icon(
                            painter = painterResource(id = if (item.data!!.title in savedNews.value.map { it.title }) R.drawable.ic_save_filled else R.drawable.ic_save_unfilled),
                            contentDescription = "Icon save",
                            tint = colors.onSurface,
                            modifier = Modifier.clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                coroutineScope.launch(Dispatchers.IO) {
                                    detailedNewsViewModel.savingMethod(item.data)
                                }
                            }
                        )
                    }
                }
            }
            is ListItemState.LoadingItemState -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.7f)
                        .background(brush)
                )
            }
        }
    }
}