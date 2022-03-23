package com.example.nuntium.ui.homePage.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nuntium.constants.Constants
import com.example.nuntium.data.locale.News
import com.example.nuntium.domain.locale.RoomRepository
import com.example.nuntium.domain.remote.ApiRepository
import com.example.nuntium.ui.appLevelStates.ListItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.Exception
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class TopicNewsViewModel @Inject constructor(
    val remote: ApiRepository,
    val locale: RoomRepository
) : ViewModel() {
    //general
    private val TAG = "HomeViewModel"
    val loadingNews = Constants.loadingDummy

    //tabVariables
    val selectedTabItem = MutableStateFlow<Int>(0)
    val tabItems = Constants.TOPICS

    //page
    val page = MutableSharedFlow<Int>()
    var currentPage = 1
    val lastVisibleIndex = MutableSharedFlow<Int>()

    //response
    val topicNews = MutableStateFlow<List<ListItemState<News>>>(emptyList())
    var loading = false

    init {
        handlePageChange()
        handleTopicChange()
        handleScrollIndex()
    }

    private fun handleScrollIndex() {
        viewModelScope.launch {
            lastVisibleIndex.collect {
                Log.d(TAG, "handleScrollIndex: index $it")
                if (it + 1 >= currentPage * 20) {
                    if (!loading) {
                        Log.d(TAG, "handleScrollIndex: ${currentPage.plus(1)}")
                        page.emit(currentPage + 1)
                    }
                }
            }
        }
    }

    private fun handleTopicChange() {
        viewModelScope.launch {
            selectedTabItem.collectLatest {
                loading = false
                topicNews.value = emptyList()
                page.emit(1)
            }
        }
    }

    private fun handlePageChange() {
        viewModelScope.launch {
            page.collectLatest {
                Log.d(TAG, "handlePageChange: $it")
                currentPage = it
                loading = true
                emitLoading()
                try {
                    val news = remote.getNews(currentPage, tabItems[selectedTabItem.value])
                    emitLoaded(news)
                    loading = false
                } catch (e: Exception) {
                    loading = false
                }
            }
        }
    }

    private fun emitLoading() {
        val oldList = topicNews.value
        viewModelScope.launch {
            topicNews.emit(oldList.toMutableList().also {
                it.addAll(loadingNews)
            }.toList())
        }
    }

    private fun emitLoaded(data: List<News>) {
        viewModelScope.launch {
            val oldList = topicNews.value.filter {
                it is ListItemState.LoadedItemState
            }
            topicNews.emit(oldList.toMutableList().also { list ->
                list.addAll(data.map { ListItemState.LoadedItemState<News>(it) })
            }.toList())
        }
    }
}
