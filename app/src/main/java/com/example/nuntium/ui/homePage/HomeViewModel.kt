package com.example.nuntium.ui.homePage

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nuntium.constants.Constants
import com.example.nuntium.data.locale.News
import com.example.nuntium.domain.locale.RoomRepository
import com.example.nuntium.domain.pagination.MyPaginator
import com.example.nuntium.domain.remote.ApiRepository
import com.example.nuntium.ui.appLevelStates.ListItemState
import com.example.nuntium.ui.homePage.states.HomePageStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val remote: ApiRepository,
    val locale: RoomRepository
) : ViewModel() {
    private val TAG = "HomeViewModel"
    //tabVariables
    val selectedTabItem = MutableStateFlow<Int>(0)
    val topicNewsPaginator = MyPaginator(remote)
    val topicNews = MutableStateFlow<List<ListItemState<News>>>(emptyList())
    val scrolledToTheEnd = MutableSharedFlow<Boolean>()

    //pageState
    val pageState = mutableStateOf<HomePageStates>(HomePageStates.CasualPage)

    init {
        handleResponse()
        viewModelScope.launch(Dispatchers.IO) {
            topicNewsPaginator.handlePageChange()
        }
        viewModelScope.launch {
            topicNewsPaginator.handleTopicChange()
        }
        viewModelScope.launch {
            topicNewsPaginator.handleScrollState()
        }
        handleTopicChange()
        handleScrollState()
    }

    private fun handleTopicChange() {
        viewModelScope.launch {
            selectedTabItem.collect {
                Log.d(TAG, "handleTopicChange: tabl Selected")
                topicNewsPaginator.topic.emit(Constants.TOPICS[it])
            }
        }
    }

    private fun handleResponse() {
        viewModelScope.launch {
            topicNewsPaginator.result.collect {
                topicNews.emit(it)
            }
        }
    }

    private fun handleScrollState() {
        viewModelScope.launch {
            scrolledToTheEnd.collect {
                topicNewsPaginator.isScrolledToTheEnd.emit(it)
            }
        }
    }
}
