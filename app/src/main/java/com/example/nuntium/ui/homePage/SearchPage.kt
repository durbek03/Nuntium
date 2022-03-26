package com.example.nuntium.ui.homePage

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.nuntium.data.locale.News
import com.example.nuntium.ui.appLevelComp.customBrush
import com.example.nuntium.ui.appLevelStates.ListItemState
import com.example.nuntium.ui.appLevelStates.WindowInfo
import com.example.nuntium.ui.appLevelStates.rememberWindowInfo
import com.example.nuntium.ui.homePage.intent.HomePageIntent
import com.example.nuntium.ui.homePage.viewModels.HomeViewModel
import com.example.nuntium.ui.homePage.viewModels.SearchViewModel
import kotlinx.coroutines.launch
import com.example.nuntium.R
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow


@Composable
fun SearchPage(
    modifier: Modifier = Modifier,
    columnState: LazyListState,
    rowState: LazyListState = rememberLazyListState()
) {
    //viewModel
    val homeViewModel: HomeViewModel = hiltViewModel()
    val searchViewModel: SearchViewModel = hiltViewModel()
    //remembers
    val coroutineScope = rememberCoroutineScope()
    val searchResponse = searchViewModel.response.collectAsState()
    //ui
    val colors = MaterialTheme.colors
    val windowInfo = rememberWindowInfo()

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
                    coroutineScope.launch {
                        homeViewModel.action.emit(HomePageIntent.OpenCasualPage)
                    }
                },
                onTextChange = { searchText ->
                    coroutineScope.launch {
                        searchViewModel.query.emit(searchText)
                    }
                },
                focusOnLaunch = true
            )
        }
        itemsIndexed(searchResponse.value) { index, item ->
            Crossfade(targetState = windowInfo.height) {
                if (it * 1.8f >= windowInfo.width) {
                    SearchItemMedium(item = item, modifier = Modifier.padding(15.dp, 5.dp))
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
                            SearchItemExtended(item = item, modifier = Modifier.padding(5.dp))
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun SearchItemMedium(modifier: Modifier = Modifier, item: ListItemState<News>) {
    val colors = MaterialTheme.colors
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(2 / 1f)
            .clip(RoundedCornerShape(15.dp))
            .background(color = colors.surface)
    ) {
        SearchItem(item = item)
    }
}

@Composable
fun SearchItemExtended(modifier: Modifier = Modifier, item: ListItemState<News>) {
    val windowInfo = rememberWindowInfo()
    val colors = MaterialTheme.colors
    Box(
        modifier = modifier
            .width(windowInfo.width / 3.5f)
            .aspectRatio(2 / 1.5f)
            .clip(RoundedCornerShape(15.dp))
            .background(color = colors.surface)
    ) {
        SearchItem(item = item)
    }
}

@Composable
fun SearchItem(item: ListItemState<News>) {
    val brush = customBrush()
    val colors = MaterialTheme.colors
    Crossfade(targetState = item) {
        when (it) {
            is ListItemState.LoadedItemState -> {
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = rememberImagePainter(item.data?.image ?: ""),
                    contentDescription = "image",
                    contentScale = ContentScale.FillBounds
                )
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
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
                            painter = painterResource(id = R.drawable.ic_save),
                            contentDescription = "Icon save",
                            tint = colors.onSurface
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