package com.example.nuntium.domain.remote

import android.util.Log
import com.example.nuntium.data.models.toNewsList
import com.example.nuntium.data.remote.ApiService
import com.example.nuntium.data.locale.News
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

class ApiRepImpl @Inject constructor(val apiService: ApiService) : ApiRepository {
    private val TAG = "ApiRepImpl"

    override suspend fun getNews(page: Int, query: String) : List<News> {
        val response = apiService.getNews(page = page, query = query).toNewsList()
        Log.d(TAG, "getNews: query $query")
        for (i in response) {
            Log.d(TAG, "getNews: ${i.title}")
            Log.d(TAG, "getNews: ${i.content}")
        }
        return response
    }
}