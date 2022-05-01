package com.example.nuntium.data.locale

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class News : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
    @ColumnInfo
    var title: String = ""
    @ColumnInfo
    var author: String? = ""
    @ColumnInfo
    var content: String = ""
    @ColumnInfo
    var image: String? = ""
    @ColumnInfo
    var source: String = ""
    @ColumnInfo
    var isSaved: Boolean = false

    constructor(
        title: String,
        author: String,
        content: String,
        image: String?,
        source: String,
        isSaved: Boolean = false
    ) {
        this.title = title
        this.author = author
        this.content = content
        this.image = image
        this.source = source
        this.isSaved = isSaved
    }

}