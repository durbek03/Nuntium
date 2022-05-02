package com.example.nuntium.ui.savedNewsPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nuntium.MainViewModel
import com.example.nuntium.ui.homePage.Recommendation
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.example.nuntium.R

@Composable
fun SavedNewsScreen(navigator: DestinationsNavigator) {
    val recommendation = Recommendation(navigator = navigator)
    val colors = MaterialTheme.colors
    val mainViewModel: MainViewModel = hiltViewModel()
    val savedNews = mainViewModel.savedNews.collectAsState()

    if (savedNews.value.isNotEmpty()) {
        LazyColumn(
            state = mainViewModel.savedNewsListState, modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 0.dp, 0.dp, 80.dp)
        ) {
            item {
                Header()
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }
            itemsIndexed(savedNews.value) { index, item ->
                recommendation.RecItem(news = item)
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 0.dp, 0.dp, 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Header()
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_book),
                    contentDescription = "icon",
                    tint = colors.primary,
                    modifier = Modifier
                        .width(65.dp)
                        .height(65.dp)
                        .clip(RoundedCornerShape(50.dp))
                        .background(color = colors.surface)
                        .padding(20.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "You haven't saved any articles yet. Start reading and bookmarking them now",
                    modifier = Modifier.fillMaxWidth(0.7f),
                    color = colors.onBackground
                )
            }
        }
    }
}

@Composable
fun Header() {
    val colors = MaterialTheme.colors
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(15.dp, 15.dp, 15.dp, 0.dp),
            text = "Bookmarks",
            color = colors.onBackground,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier.padding(15.dp, 0.dp),
            text = "Saved articles to the library",
            color = colors.onSurface,
            fontSize = 17.sp
        )
    }
}