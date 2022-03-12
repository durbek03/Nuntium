package com.example.nuntium.data.locale

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNews(news: News)

    @Query("delete from News where isSaved =:isSaved")
    suspend fun deleteNotSavedNews(isSaved: Boolean = false)

    @Delete
    fun unSaveNews(news: News)

    @Query("select * from News where isSaved =:isSaved")
    fun getSavedNews(isSaved: Boolean = true): Flow<List<News>>

    @Query("select * from News")
    fun getCachedNews(): Flow<List<News>>
}