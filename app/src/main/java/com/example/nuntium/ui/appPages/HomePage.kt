package com.example.nuntium.ui.appPages

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.example.nuntium.R
import com.example.nuntium.constants.Constants
import com.example.nuntium.data.locale.News
import com.example.nuntium.ui.Resource
import com.example.nuntium.ui.viewmodels.HomeViewModel

@OptIn(ExperimentalFoundationApi::class)
@Destination
@Composable
fun HomePage() {
    val topicList = Constants.TOPICS
    val colors = MaterialTheme.colors
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
                        .padding(15.dp, 10.dp, 15.dp, 5.dp)
                        .height(55.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(color = MaterialTheme.colors.surface)
                )
            }
            item {
                TopicsLazyRow(topics = topicList, modifier = Modifier.fillMaxWidth())
            }
            item {
                TopicNewsLazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.7f / 1f)
                )
            }
        }
    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "Search",
            tint = MaterialTheme.colors.onSurface
        )
        TextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(0.7f),
            singleLine = true,
            placeholder = {
                Text(
                    text = "Search",
                    color = MaterialTheme.colors.onSurface,
                    fontWeight = FontWeight.Bold
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colors.onSurface,
                cursorColor = MaterialTheme.colors.onSurface,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent
            )
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_microphone),
            contentDescription = "Search",
            tint = MaterialTheme.colors.onSurface
        )
    }
}

@Composable
fun TopicsLazyRow(modifier: Modifier = Modifier, topics: List<String>) {
    val selectedTopic = remember {
        mutableStateOf(0)
    }
    LazyRow(modifier = modifier.fillMaxWidth()) {
        itemsIndexed(items = topics) { index, topic ->
            val boxColor =
                animateColorAsState(targetValue = if (selectedTopic.value == index) MaterialTheme.colors.primary else MaterialTheme.colors.surface)
            val textColor =
                animateColorAsState(targetValue = if (selectedTopic.value == index) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface)
            Box(
                modifier = Modifier
                    .padding(
                        if (index == 0) 15.dp else 5.dp,
                        5.dp,
                        if (index == topics.lastIndex) 15.dp else 5.dp,
                        5.dp
                    )
                    .defaultMinSize(minWidth = 100.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .background(boxColor.value)
                    .clickable { selectedTopic.value = index },
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

@OptIn(ExperimentalCoilApi::class)
@Composable
fun TopicNewsLazyRow(modifier: Modifier = Modifier) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    Crossfade(targetState = homeViewModel.tabResults.value) {
        val colors = listOf<Color>(
            MaterialTheme.colors.onSurface.copy(alpha = 0.35f),
            MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
            MaterialTheme.colors.onSurface.copy(alpha = 0.35f)
        )
        val transition = rememberInfiniteTransition()
        val translate = transition.animateFloat(
            initialValue = 0f,
            targetValue = 1000f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000, easing = FastOutLinearInEasing)
            )
        )
        val brush = Brush.linearGradient(
            colors, start = Offset.Zero, end = Offset(x = translate.value, y = translate.value)
        )

        LazyRow(modifier = modifier, horizontalArrangement = Arrangement.Center) {
            when (it) {
                is Resource.Error -> {
                    item {
                        Box(
                            modifier = Modifier
                                .padding(5.dp)
                                .fillMaxSize()
                                .border(
                                    1.dp,
                                    color = MaterialTheme.colors.onBackground,
                                    shape = RoundedCornerShape(15.dp)
                                )
                                .padding(10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Error occured",
                                color = MaterialTheme.colors.onBackground,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                is Resource.Loading -> {
                    items(10) { index ->
                        Box(
                            modifier = Modifier
                                .padding(
                                    if (index == 0) 15.dp else 5.dp,
                                    5.dp,
                                    if (index == 10) 15.dp else 5.dp,
                                    5.dp
                                )
                                .fillMaxHeight()
                                .aspectRatio(1f, true)
                                .clip(RoundedCornerShape(15.dp))
                                .background(brush)
                        )
                    }
                }
                is Resource.Success -> {
                    itemsIndexed(items = it.data ?: emptyList()) { index: Int, item: News ->
                        Box(
                            modifier = Modifier
                                .padding(
                                    if (index == 0) 15.dp else 7.5.dp,
                                    5.dp,
                                    if (index == it.data?.lastIndex) 15.dp else 7.5.dp,
                                    5.dp
                                )
                                .fillMaxHeight()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(15.dp))
                                .background(Color.Black)
                        ) {
                            Image(
                                painter = rememberImagePainter(item.image),
                                contentDescription = null,
                                modifier = Modifier.size(128.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}