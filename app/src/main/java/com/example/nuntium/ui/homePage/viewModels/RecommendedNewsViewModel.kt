package com.example.nuntium.ui.homePage.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nuntium.constants.Constants
import com.example.nuntium.data.locale.News
import com.example.nuntium.domain.remote.ApiRepository
import com.example.nuntium.ui.appLevelStates.ListItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RecommendedNewsViewModel @Inject constructor(
    val apiRepository: ApiRepository
) : ViewModel() {
    private val TAG = "RecommendedNewsViewMode"
    private val loadingNews = Constants.loadingDummy
    private val page = MutableSharedFlow<Int>()
    private var currentPage = 1
    val query = MutableStateFlow<String>("")
    var pickedTopics = mutableStateListOf<String>()
    val recommendNews = MutableStateFlow<List<ListItemState<News>>>(emptyList())
    val scrollIndex = MutableSharedFlow<Int>()
    var loading = false

    init {
        Log.d(TAG, "init: init")
        handlePageChange()
        handleTopicChange()
        handleScrollIndex()
    }

    private fun handleScrollIndex() {
        viewModelScope.launch {
            scrollIndex.collect {
                Log.d(TAG, "handleScrollIndex: index: $it")
                if (it == currentPage * 20 + 2) {
                    if (!loading) {
                        Log.d(TAG, "handleScrollIndex: loadNextPage")
                        page.emit(currentPage + 1)
                    }
                }
            }
        }
    }

    private fun handlePageChange() {
        viewModelScope.launch {
            page.collectLatest {
                Log.d(TAG, "handlePageChange: $it ")
                currentPage = it
                loading = true
                emitLoading()
                try {
                    val news = apiRepository.getNews(it, query = query.value)
                    emitLoaded(news)
                    loading = false
                } catch (e: Exception) {
                    Log.d(TAG, "handlePageChange: exception")
                    loading = false
                }
            }
        }
    }

    private fun handleTopicChange() {
        viewModelScope.launch {
            query.collectLatest {
                if (it.isEmpty()) {
                    query.value = "random"
                }
                Log.d(TAG, "handleTopicChange: $it")
                loading = false
                recommendNews.value = emptyList()
                page.emit(1)
            }
        }
    }

    private fun emitLoading() {
        val oldList = recommendNews.value
        viewModelScope.launch {
            recommendNews.emit(oldList.toMutableList().also {
                it.addAll(loadingNews)
            }.toList())
        }
    }

    private fun emitLoaded(data: List<News>) {
        viewModelScope.launch {
            val oldList = recommendNews.value.filter {
                it is ListItemState.LoadedItemState
            }
            recommendNews.emit(oldList.toMutableList().also { list ->
                list.addAll(data.map { ListItemState.LoadedItemState<News>(it) })
            }.toList())
        }
    }
}