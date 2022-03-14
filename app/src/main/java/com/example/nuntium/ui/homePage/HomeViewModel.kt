package com.example.nuntium.ui.homePage

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nuntium.data.locale.News
import com.example.nuntium.domain.locale.RoomRepository
import com.example.nuntium.domain.pagination.MyPaginator
import com.example.nuntium.domain.remote.ApiRepository
import com.example.nuntium.ui.Resource
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
    var selectedTabTopic = "Sport"
    val topicNewsPaginator = MyPaginator(remote)
    val topicNews = mutableStateOf<Resource<List<News>>>(Resource.Loading())
    val topicNewsScrollPosition = MutableStateFlow<Int>(0)

    //pageState
    val pageState = mutableStateOf<HomePageStates>(HomePageStates.CasualPage)

    init {
        viewModelScope.launch {
            topicNewsPaginator.handleTopicChange()
        }
        handleTopicChange()
        handleResponse()
        handleScrollPositionChanged()
        viewModelScope.launch(Dispatchers.IO) {
            topicNewsPaginator.handlePageChange()
        }
    }

    private fun handleTopicChange() {
        viewModelScope.launch {
            selectedTabItem.collect {
                Log.d(TAG, "handleTopicChange: $selectedTabTopic")
                viewModelScope.launch {
                    topicNewsPaginator.topic.emit(selectedTabTopic)
                }
            }
        }
    }

    private fun handleResponse() {
        viewModelScope.launch {
            topicNewsPaginator.response.collect {
                topicNews.value = it
            }
        }
    }

    fun handleScrollPositionChanged() {
        viewModelScope.launch {
            topicNewsScrollPosition.collect {
                Log.d(TAG, "handleScrollPositionChanged: $it")
                viewModelScope.launch {
                    topicNewsPaginator.onScrollPositionChanged(it)
                }
            }
        }
    }
}
