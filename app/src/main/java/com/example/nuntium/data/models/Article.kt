package com.example.nuntium.data.models


import com.example.nuntium.data.locale.News
import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("author")
    val author: String?,
    @SerializedName("content")
    val content: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("source")
    val source: Source,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("urlToImage")
    val urlToImage: String
)

fun Article.toNews(): News {
    return News(
        title = this.title,
        author = this.author ?: "",
        content = this.content,
        image = this.urlToImage,
        source = this.source.name,
    )
}