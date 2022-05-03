package com.example.nuntium.data.locale

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AppLanguageDao {
    @Insert
    fun changeLanguage(language: AppLanguage)

    @Query("delete from AppLanguage")
    fun clearTable()

    @Query("select * from AppLanguage")
    fun getLanguage(): Flow<List<AppLanguage>>
}