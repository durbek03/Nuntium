package com.example.nuntium

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
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
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.example.nuntium.ui.NavGraphs
import com.example.nuntium.ui.destinations.*
import com.example.nuntium.ui.detailedNewsPage.NewsDetailedScreen
import com.example.nuntium.ui.entrancePage.PickTopicPage
import com.example.nuntium.ui.homePage.HomePage
import com.example.nuntium.ui.homePage.viewModels.HomeViewModel
import com.example.nuntium.ui.homePage.viewModels.RecommendedNewsViewModel
import com.example.nuntium.ui.mainAppPage.MainAppScreen
import com.example.nuntium.ui.profilePage.LanguagesScreen
import com.example.nuntium.ui.searchPage.SearchPage
import com.example.nuntium.ui.theme.NuntiumTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var handler: Handler
    private val TAG = "MainActivity"
    lateinit var homeViewModel: HomeViewModel
    lateinit var mainViewModel: MainViewModel
    var leavable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        handler = Handler(Looper.getMainLooper())
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
                        composable(SearchPageDestination) {
                            CompositionLocalProvider(
                                LocalViewModelStoreOwner provides viewModelStoreOwner
                            ) {
                                SearchPage(navigator = destinationsNavigator)
                            }
                        }
                        composable(NewsDetailedScreenDestination) {
                            CompositionLocalProvider(
                                LocalViewModelStoreOwner provides viewModelStoreOwner
                            ) {
                                val news = this.navArgs.news
                                NewsDetailedScreen(
                                    navigator = destinationsNavigator,
                                    news = news,
                                    context = this@MainActivity
                                )
                            }
                        }
                        composable(LanguagesScreenDestination) {
                            CompositionLocalProvider(
                                LocalViewModelStoreOwner provides viewModelStoreOwner
                            ) {
                                LanguagesScreen(navigator = destinationsNavigator)
                            }
                        }
                        composable(MainAppScreenDestination) {
                            CompositionLocalProvider(
                                LocalViewModelStoreOwner provides viewModelStoreOwner
                            ) {
                                MainAppScreen(destinationsNavigator)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        val canBackPress = mainViewModel.canBackPress.value
        if (canBackPress) {
            if (leavable) {
                super.onBackPressed()
            } else {
                Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show()
                handler.postDelayed({
                    leavable = false
                }, 2000)
                leavable = true
            }
        } else {
            super.onBackPressed()
        }
    }
}