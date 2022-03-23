package com.example.nuntium

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.example.nuntium.ui.NavGraphs
import com.example.nuntium.ui.destinations.HomePageDestination
import com.example.nuntium.ui.destinations.PickTopicPageDestination
import com.example.nuntium.ui.entrancePage.PickTopicPage
import com.example.nuntium.ui.homePage.HomePage
import com.example.nuntium.ui.homePage.viewModels.RecommendedNewsViewModel
import com.example.nuntium.ui.theme.NuntiumTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NuntiumTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
                        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
                    }
                    DestinationsNavHost(navGraph = NavGraphs.root) {
                        composable(PickTopicPageDestination) {
                            CompositionLocalProvider(
                                LocalViewModelStoreOwner provides viewModelStoreOwner
                            ) {
                                PickTopicPage(navigator = destinationsNavigator)
                            }
                        }
                        composable(HomePageDestination) {
                            CompositionLocalProvider(
                                LocalViewModelStoreOwner provides viewModelStoreOwner
                            ) {
                                HomePage()
                            }
                        }
                    }
                }
            }
        }
    }
}