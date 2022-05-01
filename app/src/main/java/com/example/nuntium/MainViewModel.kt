package com.example.nuntium

import androidx.lifecycle.ViewModel
import com.example.nuntium.ui.mainAppPage.MainAppScreenStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    val mainAppScreenState = MutableStateFlow<MainAppScreenStates>(MainAppScreenStates.HomePage)
}