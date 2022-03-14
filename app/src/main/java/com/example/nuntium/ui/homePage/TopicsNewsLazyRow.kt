package com.example.nuntium.ui.homePage

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.nuntium.data.locale.News
import com.example.nuntium.ui.Resource
import com.example.nuntium.R


@OptIn(ExperimentalCoilApi::class)
@Composable
fun TopicNewsLazyRow(modifier: Modifier = Modifier) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val topicNews = homeViewModel.topicNews
    Crossfade(targetState = topicNews.value) {
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

        val lazyRowState = rememberLazyListState()
        val firstVisibleItemIndex = lazyRowState.firstVisibleItemIndex
        LaunchedEffect(key1 = firstVisibleItemIndex) {
            homeViewModel.topicNewsScrollPosition.emit(firstVisibleItemIndex)
        }
        LazyRow(
            state = lazyRowState,
            modifier = modifier,
            horizontalArrangement = Arrangement.Center,
            contentPadding = PaddingValues(5.dp, 0.dp)
        ) {
            when (it) {
                is Resource.Error -> {
                    item {
                        Box(
                            modifier = Modifier
                                .padding(5.dp, 0.dp)
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
                                .padding(5.dp, 0.dp)
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
                                .padding(5.dp, 0.dp)
                                .fillMaxHeight()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(15.dp)),
                        ) {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(15.dp)),
                                painter = rememberImagePainter(item.image),
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
                                        .padding(15.dp),
                                    text = item.title,
                                    fontSize = 17.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Left
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
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}