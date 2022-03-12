package com.example.nuntium.data.models


import com.example.nuntium.data.locale.News
import com.google.gson.annotations.SerializedName

data class NewsDto(
    @SerializedName("articles")
    val articles: List<Article>,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)

fun NewsDto.toNewsList() : List<News> {
    val list = arrayListOf<News>()
    this.articles.forEach {
        list.add(it.toNews())
    }
    return list
}