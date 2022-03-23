package com.example.nuntium.domain.remote

import android.util.Log
import com.example.nuntium.data.models.toNewsList
import com.example.nuntium.data.remote.ApiService
import com.example.nuntium.data.locale.News
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.supervisorScope
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class ApiRepImpl @Inject constructor(val apiService: ApiService) : ApiRepository {
    private val response = mutableListOf<News>()
    private val TAG = "ApiRepImpl"

    init {
        loadData()
    }

    override suspend fun getNews(page: Int, query: String): List<News> {
        Log.d(TAG, "getNews: request")
        delay(1000)
        //      val response = apiService.getNews(page = page, query = query).toNewsList()
        return try {
         //   return apiService.getNews(query = query, page = page).toNewsList()
            return response
        } catch (e:java.lang.Exception) {
            emptyList()
        }
    }

    fun loadData() {
        for (i in 1..20) {
            response.add(
                News(
                    "title$i",
                    "some author",
                    "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content. Lorem ipsum may be used as a placeholder before the final copy is",
                    "https://upload.wikimedia.org/wikipedia/en/2/21/Web_of_Spider-Man_Vol_1_129-1.png",
                    "source",
                    false
                )
            )
        }
    }
}