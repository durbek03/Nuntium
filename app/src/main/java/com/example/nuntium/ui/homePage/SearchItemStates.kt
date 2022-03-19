package com.example.nuntium.ui.homePage

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import com.example.nuntium.data.locale.News
import com.example.nuntium.ui.appLevelComp.customBrush
import com.example.nuntium.ui.appLevelStates.ListItemState

@Composable
fun SearchItemStates(state: ListItemState<News>) {
    Crossfade(targetState = state) {
        val brush = customBrush()
        when (it) {
            is ListItemState.LoadedItemState -> SearchLoaded(news = state.data!!)
            is ListItemState.LoadingItemState -> SearchLoading(brush = brush)
        }
    }
}

@Composable
fun SearchLoaded(news: News) {

}

@Composable
fun SearchLoading(brush: Brush) {

}