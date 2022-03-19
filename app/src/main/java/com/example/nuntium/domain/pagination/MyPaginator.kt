package com.example.nuntium.domain.pagination

import android.util.Log
import com.example.nuntium.constants.Constants
import com.example.nuntium.data.locale.News
import com.example.nuntium.domain.remote.ApiRepository
import com.example.nuntium.ui.appLevelStates.ListItemState
import kotlinx.coroutines.flow.*

class MyPaginator(val repository: ApiRepository) {
    private val TAG = "MyPaginator"
    val page = MutableSharedFlow<Int>()
    var currentPage = 1
    val topic = MutableStateFlow("")
    val result = MutableStateFlow<List<ListItemState<News>>>(emptyList())
    val isScrolledToTheEnd = MutableSharedFlow<Boolean>()
    val loadingDummy = Constants.loadingDummy.toList()

    suspend fun handlePageChange() {
        page.collectLatest { currentPage ->
            if (result.value.contains(ListItemState.LoadingItemState())) {
                return@collectLatest
            }
            this.currentPage =currentPage
            emitLoading()
            Log.d(TAG, "handlePageChange: page Changed")
            try {
                val news = repository.getNews(currentPage, topic.value)
                Log.d(TAG, "handlePageChange: ${news.toString()}")
                emitLoaded(news)
            } catch (e: Exception) {
                Log.d(TAG, "handlePageChange: ${e.localizedMessage}")
                emitLoaded(emptyList())
                e.printStackTrace()
            }
        }
    }

    suspend fun handleTopicChange() {
        topic.collectLatest { currentTopic ->
            result.emit(emptyList())
            Log.d(TAG, "handleTopicChange: $currentTopic")
            page.emit(1)
        }
    }

    private suspend fun emitLoading() {
        result.emit(result.value.toMutableList().also { list ->
            list.addAll(loadingDummy)
        }.toList())
    }

    private suspend fun emitLoaded(response: List<News>) {
        val loadedItems = result.value.filter { it is ListItemState.LoadedItemState }
        result.emit(loadedItems.toMutableList().also { list ->
            list.addAll(response.map { ListItemState.LoadedItemState(it) })
        }.toList())
    }

    suspend fun handleScrollState() {
        isScrolledToTheEnd.collect {
            if (it) {
                page.emit(currentPage + 1)
            }
        }
    }
}