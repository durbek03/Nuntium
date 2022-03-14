package com.example.nuntium.domain.pagination

import android.util.Log
import com.example.nuntium.constants.Constants
import com.example.nuntium.data.locale.News
import com.example.nuntium.domain.remote.ApiRepository
import com.example.nuntium.ui.Resource
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

class MyPaginator(val repository: ApiRepository) {
    private val TAG = "MyPaginator"
    private var page = MutableStateFlow(1)
    private var scrollPosition = 0
    private val pageSize = Constants.PAGE_SIZE
    var topic = MutableSharedFlow<String>()
    var currentTopic = ""
    val response = MutableStateFlow<Resource<List<News>>>(Resource.Loading())

    suspend fun handlePageChange() {
        page.collectLatest {
            Log.d(TAG, "current page $it")
            if (it == 1) {
                response.emit(Resource.Loading(data = response.value.data))
            }
            if (currentTopic.isEmpty()) {
                return@collectLatest
            }
            try {
                val result = repository.getNews(it, currentTopic)
                val mergedData = mergeData(result)
                for (i in result) {
                    Log.d(TAG, "result: success ${if (i.title.isEmpty()) "false" else i.author}")
                }
                response.emit(Resource.Success(data = mergedData))
            } catch (e: Exception) {
                Log.d(TAG, "handlePageChange: ${e.localizedMessage}")
                response.emit(Resource.Error(message = e.localizedMessage ?: "Error"))
            }
        }
    }

    suspend fun handleTopicChange() {
        topic.collectLatest { topic ->
            currentTopic = topic
            response.emit(Resource.Loading(data = null))
            scrollPosition = 0
            page.value = 1
        }
    }

    fun mergeData(data: List<News>): List<News> {
        val newList = arrayListOf<News>()
        val oldList = response.value
        newList.addAll(oldList.data ?: emptyList())
        newList.addAll(data)
        return newList.toList()
    }

    suspend fun onScrollPositionChanged(position: Int) {
        if (position + 2 >= page.value * pageSize) {
            incrementPage()
        }
        scrollPosition = position
    }

    suspend fun incrementPage() {
        Log.d(TAG, "incrementPage: page incremented")
        page.emit(page.value + 1)
        Log.d(TAG, "incrementPage: ${page.value}")
    }
}