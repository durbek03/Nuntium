package com.example.nuntium.ui.homePage

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.example.nuntium.data.locale.News
import com.example.nuntium.ui.appLevelComp.customBrush
import com.example.nuntium.ui.appLevelStates.ListItemState
import com.example.nuntium.ui.destinations.Destination
import com.example.nuntium.ui.destinations.NewsDetailedScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

class Recommendation(val navigator: DestinationsNavigator) {
    @Composable
    fun RecHeader(modifier: Modifier = Modifier) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Recommended for you",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = MaterialTheme.colors.onBackground
            )
            Text(text = "See All", fontSize = 17.sp, color = MaterialTheme.colors.onSurface)
        }
    }

    @Composable
    fun RecItemStates(itemState: ListItemState<News>, modifier: Modifier = Modifier) {
        val brush = customBrush()
        Crossfade(targetState = itemState) {
            when (it) {
                is ListItemState.LoadedItemState -> {
                    RecItem(news = it.data!!, modifier = modifier)
                }
                is ListItemState.LoadingItemState -> {
                    LoadingRecItem(brush)
                }
            }
        }
    }

    @Composable
    fun RecItem(modifier: Modifier = Modifier, news: News) {
        Row(
            modifier = modifier
                .padding(5.dp, 5.dp)
                .clickable {
                    navigator.navigate(NewsDetailedScreenDestination(news = news))
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Image(
                modifier = Modifier
                    .padding(5.dp, 0.dp)
                    .fillMaxWidth(0.3f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(15.dp)),
                painter = rememberImagePainter(news.image),
                contentDescription = news.title,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(5.dp, 0.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    modifier = Modifier,
                    text = news.source,
                    fontSize = 17.sp,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier,
                    text = news.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colors.onBackground,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

    @Composable
    fun LoadingRecItem(brush: Brush) {
        Row(
            modifier = Modifier.padding(5.dp, 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Box(
                modifier = Modifier
                    .padding(5.dp, 0.dp)
                    .fillMaxWidth(0.3f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(15.dp))
                    .background(brush),
            )
            Column(
                modifier = Modifier
                    .padding(5.dp, 0.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(50.dp)
                        .padding(10.dp, 5.dp)
                        .background(brush),
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(50.dp)
                        .padding(10.dp, 5.dp)
                        .background(brush),
                )
            }
        }
    }
}