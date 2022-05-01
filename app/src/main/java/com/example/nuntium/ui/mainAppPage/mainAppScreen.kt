package com.example.nuntium.ui.mainAppPage

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nuntium.MainViewModel
import com.example.nuntium.ui.homePage.HomePage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun MainAppScreen(navigator: DestinationsNavigator) {
    val mainViewModel: MainViewModel = hiltViewModel()
    val colors = MaterialTheme.colors
    Box(modifier = Modifier.fillMaxSize()) {
        Crossfade(targetState = mainViewModel.mainAppScreenState) {
            val screen = it.collectAsState().value
            when (screen) {
                MainAppScreenStates.HomePage -> {
                    HomePage(
                        navigator = navigator, modifier = Modifier
                            .fillMaxSize()
                            .background(color = colors.background)
                            .padding(0.dp, 0.dp, 0.dp, 50.dp)
                    )
                }
                MainAppScreenStates.TopicPickPage -> {}
                MainAppScreenStates.SavedNewsPage -> {}
                MainAppScreenStates.ProfilePage -> {}
            }
        }
        BottomNavigationBar(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(colors.background)
                .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
                .border(width = 1.dp, color = colors.surface)
        )
    }
}