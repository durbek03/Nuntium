package com.example.nuntium.data.locale

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveNews(news: News)

    @Query("delete from News where title = :title")
    fun unSaveNews(title: String)

    @Query("select * from News")
    fun getSavedNews(): Flow<List<News>>

    @Query("select * from News where title = :title")
    fun checkIfExists(title: String): News?
}