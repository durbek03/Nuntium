package com.example.nuntium.data.locale

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.nuntium.constants.AppThemeMode
import kotlinx.coroutines.flow.Flow

@Dao
interface AppThemeDao {

    @Insert
    fun changeTheme(appThemeModeRoom: AppThemeModeRoom)

    @Query("delete from AppThemeModeRoom")
    fun dropTable()

    @Query("select * from AppThemeModeRoom")
    fun getThemeMode() : Flow<List<AppThemeModeRoom>>
}