package com.example.nuntium.ui.homePage

import android.util.Log
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
import javax.inject.Inject

@HiltViewModel
class RecommendedNewsViewModel @Inject constructor(
    val apiRepository: ApiRepository
) : ViewModel() {
    private val TAG = "RecommendedNewsViewMode"
    private var currentPage = 1
    private val page = MutableSharedFlow<Int>()
    private var query = ""
    private val loadingNews = Constants.loadingDummy
    var pickedTopics = MutableStateFlow<SnapshotStateList<String>>(SnapshotStateList())
    val recommendNews = MutableStateFlow<List<ListItemState<News>>>(emptyList())
    val isScrolledToEnd = MutableSharedFlow<Boolean>()

    init {
        handlePageChange()
        handleTopicChange()
        handleScrollState()
    }

    private fun handleScrollState() {
        viewModelScope.launch {
            isScrolledToEnd.collectLatest {
                if (it) {
                    page.emit(currentPage + 1)
                }
            }
        }
    }

    private fun handlePageChange() {
        viewModelScope.launch {
            page.collectLatest {
                if (recommendNews.value.contains(ListItemState.LoadingItemState())) {
                    return@collectLatest
                }
                currentPage = it
                emitLoading()
                val news = apiRepository.getNews(it, query = query)
                Log.d(TAG, "handlePageChange: ${news.toString()}")
                emitLoaded(news)
            }
        }
    }

    private fun handleTopicChange() {
        viewModelScope.launch {
            pickedTopics.collectLatest { list ->
                query = ""
                list.forEach { query += it }
                page.emit(1)
                recommendNews.value = emptyList()
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