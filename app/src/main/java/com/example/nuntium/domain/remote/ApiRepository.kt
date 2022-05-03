package com.example.nuntium.domain.remote

import com.example.nuntium.data.locale.News

interface ApiRepository {
    suspend fun getNews(page: Int, query: String, language: String = "en") : List<News>
}