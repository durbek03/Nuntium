package com.example.nuntium.domain.locale

import com.example.nuntium.data.locale.News
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    fun saveNews(news: News)
    suspend fun deleteNotSavedNews()
    fun unSaveNews(news: News)
    fun getSavedNews(): Flow<List<News>>
    fun getCachedNews(): Flow<List<News>>
}