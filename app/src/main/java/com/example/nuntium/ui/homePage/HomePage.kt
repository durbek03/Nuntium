package com.example.nuntium.ui.homePage

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.example.nuntium.R
import com.example.nuntium.constants.Constants
import com.example.nuntium.data.locale.News
import com.example.nuntium.ui.Resource
import com.example.nuntium.ui.homePage.states.HomePageStates

@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun HomePage() {
    val colors = MaterialTheme.colors
    val viewModel: HomeViewModel = hiltViewModel()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colors.background)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
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
                        .padding(15.dp, 5.dp, 15.dp, 0.dp)
                        .height(55.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(color = MaterialTheme.colors.surface)
                )
            }
            item {
                Crossfade(targetState = viewModel.pageState.value) {
                    when (it) {
                       is HomePageStates.CasualPage -> CasualPage()
                       is HomePageStates.SearchOn -> SearchPage()
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
                .aspectRatio(1.7f / 1f)
        )
    }
}

@Composable
fun SearchPage() {
    Text(text = "Search")
}