package com.example.nuntium.domain.pagination

import com.example.nuntium.constants.Constants
import com.example.nuntium.data.locale.News
import com.example.nuntium.domain.remote.ApiRepository
import com.example.nuntium.ui.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect

class MyPaginator(val repository: ApiRepository, val methodType: MethodType) {
    private var page = MutableStateFlow(0)
    val response = MutableStateFlow<Resource<List<News>>>(Resource.Loading())
    var query = ""
    private var scrollPosition = 0
    private val pageSize = Constants.PAGE_SIZE

    suspend fun handlePageChange() {
        page.collect {
            response.emit(Resource.Loading())
            when (methodType) {
                is MethodType.TopHeadlines -> {
                    try {
                        val topHeadlines = repository.getTopHeadlines(it)
                        val mergedData = mergeData(topHeadlines)
                        response.emit(Resource.Success(data = mergedData))
                    } catch (e: Exception) {
                        response.emit(Resource.Error(message = e.localizedMessage ?: "Error"))
                    }
                }
                MethodType.Search -> {
                    try {
                        val news = repository.getNews(it, query)
                        val mergedData = mergeData(news)
                        response.emit(Resource.Success(data = mergedData))
                    } catch (e: Exception) {
                        response.emit(Resource.Error(message = e.localizedMessage ?: "Error"))
                    }
                }
            }
        }
    }

    fun mergeData(data: List<News>): List<News> {
        val oldList = this.response.value
        val newList = mutableListOf<News>()
        newList.addAll(oldList.data ?: emptyList())
        newList.addAll(data)
        return newList
    }

    fun onScrollPositionChanged(position: Int) {
        if (position + 1 >= page.value * pageSize) {
            incrementPage()
        }
        scrollPosition = position
    }

    fun incrementPage() {
        page.value = page.value + 1
    }

    sealed class MethodType {
        object TopHeadlines : MethodType()
        object Search : MethodType()
    }
}