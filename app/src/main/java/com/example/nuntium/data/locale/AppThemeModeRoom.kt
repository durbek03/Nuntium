package com.example.nuntium.data.locale

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nuntium.constants.AppThemeMode

@Entity
class AppThemeModeRoom {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ColumnInfo
    var isDarkMode: Boolean = false

    constructor()
    constructor(id: Int?, isDarkMode: Boolean) {
        this.id = id
        this.isDarkMode = isDarkMode
    }
}