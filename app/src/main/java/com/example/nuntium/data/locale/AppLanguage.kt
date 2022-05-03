package com.example.nuntium.data.locale

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class AppLanguage {
    @PrimaryKey(autoGenerate = false)
    var id: Int? = 1
    @ColumnInfo
    var language: String = "en"

    constructor(id: Int, language: String) {
        this.id = id
        this.language = language
    }
}