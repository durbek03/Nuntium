package com.example.nuntium

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nuntium.data.locale.News
import com.example.nuntium.domain.locale.RoomRepository
import com.example.nuntium.ui.mainAppPage.MainAppScreenStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val locale: RoomRepository) : ViewModel() {
    private val TAG = "MainViewModel"
    val mainAppScreenState = MutableStateFlow<MainAppScreenStates>(MainAppScreenStates.HomePage)
    val columnState: LazyListState = LazyListState()
    val topicListState: LazyListState = LazyListState()
    val topicNewsListState: LazyListState = LazyListState()

    val savedNewsListState: LazyListState = LazyListState()

    init {
        loadSavedNews()
    }

    val savedNews = MutableStateFlow<List<News>>(emptyList())

    private fun loadSavedNews() {
        viewModelScope.launch(Dispatchers.IO) {
            locale.getSavedNews().collectLatest {
                Log.d(TAG, "loadSavedNews: change detected: ${it.toString()}")
                savedNews.emit(it)
            }
        }
    }
}