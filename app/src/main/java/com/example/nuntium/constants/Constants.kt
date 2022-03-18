package com.example.nuntium.constants

import com.example.nuntium.data.locale.News
import com.example.nuntium.ui.appLevelStates.ListItemState

object Constants {
    const val API_KEY = "b8445f728c1846319619d8a18f2fefef"
    val TOPICS = listOf<String>(
        "Sport",
        "Politics",
        "Life",
        "Gaming",
        "Animals",
        "Nature",
        "Food",
        "Art",
        "History",
        "Fashion"
    )
    const val BASE_URL = "https://newsapi.org/v2/"
    const val PAGE_SIZE = 20
    val dummyNews = mutableListOf<News>()
    val loadingDummy = mutableListOf<ListItemState.LoadingItemState<News>>()

    init {
        loadDummyNews()
        loadDummyLoading()
    }

    fun loadDummyLoading() {
        for (i in 1..20) {
            loadingDummy.add(ListItemState.LoadingItemState<News>())
        }
    }

    fun loadDummyNews() {
        for (i in 1..20) {
            dummyNews.add(
                News(
                    "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to $i",
                    "some author",
                    "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to demonstrate the visual form of a document or a typeface without relying on meaningful content. Lorem ipsum may be used as a placeholder before the final copy is",
                    "https://upload.wikimedia.org/wikipedia/en/2/21/Web_of_Spider-Man_Vol_1_129-1.png",
                    "In publishing and graphic design, Lorem ipsum is a placeholder text commonly used to ",
                    false
                )

            )
        }
    }
}