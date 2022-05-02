package com.example.nuntium.data.locale

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [News::class, AppThemeModeRoom::class], version = 2)
abstract class AppDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun appThemeDao(): AppThemeDao
}