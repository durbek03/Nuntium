package com.example.nuntium.domain.remote

import com.example.nuntium.data.models.toNewsList
import com.example.nuntium.data.remote.ApiService
import com.example.nuntium.data.locale.News
import javax.inject.Inject

class ApiRepImpl @Inject constructor(val apiService: ApiService) : ApiRepository {
    override suspend fun getTopHeadlines(page: Int): List<News> {
        return apiService.getTopHeadline(page).toNewsList()
    }

    override suspend fun getNews(page: Int, query: String): List<News> {
        return apiService.getNews(page = page, query = query).toNewsList()
    }
}