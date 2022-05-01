package com.example.nuntium.ui.homePage.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nuntium.ui.homePage.intent.HomePageIntent
import com.example.nuntium.ui.homePage.states.HomePageStates
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val TAG = "HomeViewModel"
    val action = MutableSharedFlow<HomePageIntent>()
    val homePageState = MutableStateFlow<HomePageStates>(HomePageStates.CasualPage)
    val backPressEnabled = MutableStateFlow<Boolean>(true)

    init {
        Log.d(TAG, "init: init")
        viewModelScope.launch {
            action.collectLatest {
                when (it) {
                    HomePageIntent.OpenCasualPage -> {
                        homePageState.emit(HomePageStates.CasualPage)
                    }
                    HomePageIntent.OpenSearchPage -> {
                        homePageState.emit(HomePageStates.SearchOn)
                    }
                }
            }
        }
    }
}