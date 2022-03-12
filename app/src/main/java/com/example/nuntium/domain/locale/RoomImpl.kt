package com.example.nuntium.domain.locale

import com.example.nuntium.data.locale.News
import com.example.nuntium.data.locale.NewsDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomImpl @Inject constructor(private val newsDao: NewsDao) : RoomRepository {
    override fun saveNews(news: News) {
        newsDao.saveNews(news = news)
    }

    override suspend fun deleteNotSavedNews() {
        newsDao.deleteNotSavedNews(false)
    }

    override fun unSaveNews(news: News) {
        newsDao.unSaveNews(news = news)
    }

    override fun getSavedNews(): Flow<List<News>> {
        return newsDao.getSavedNews()
    }

    override fun getCachedNews(): Flow<List<News>> {
        return newsDao.getCachedNews()
    }

}