package com.example.nuntium.data.remote

import com.example.nuntium.constants.Constants
import com.example.nuntium.data.models.NewsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("apiKey") apikey: String = Constants.API_KEY,
        @Query("language") language: String = "en"
    ): NewsDto

    @GET("top-headlines?country=us")
    suspend fun getTopHeadline(
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ): NewsDto
}