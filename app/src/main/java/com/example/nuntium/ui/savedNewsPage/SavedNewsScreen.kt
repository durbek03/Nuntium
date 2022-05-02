package com.example.nuntium.ui.savedNewsPage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nuntium.MainViewModel
import com.example.nuntium.ui.homePage.Recommendation
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun SavedNewsScreen(navigator: DestinationsNavigator) {
    val recommendation = Recommendation(navigator = navigator)
    val colors = MaterialTheme.colors
    val mainViewModel: MainViewModel = hiltViewModel()
    val savedNews = mainViewModel.savedNews.collectAsState()
    LazyColumn(
        state = mainViewModel.savedNewsListState, modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 0.dp, 0.dp, 80.dp)
    ) {
        item {
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

        itemsIndexed(savedNews.value) { index, item ->
            recommendation.RecItem(news = item)
        }
    }
}