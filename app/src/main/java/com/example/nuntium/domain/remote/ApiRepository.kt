package com.example.nuntium.domain.remote

import com.example.nuntium.data.locale.News

interface ApiRepository {
    suspend fun getTopHeadlines(page: Int): List<News>
    suspend fun getNews(page: Int, query: String): List<News>
}