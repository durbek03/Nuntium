package com.example.nuntium.data.locale

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [News::class, AppThemeModeRoom::class, AppLanguage::class], version = 3)
abstract class AppDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao
    abstract fun appThemeDao(): AppThemeDao
    abstract fun appLanguageDao(): AppLanguageDao
}