package com.example.nuntium.ui.detailedNewsPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nuntium.data.locale.News
import com.example.nuntium.domain.locale.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailedNewsViewModel @Inject constructor(val locale: RoomRepository) : ViewModel() {
    fun checkIfSaved(news: News): Boolean {
        return locale.checkIfExists(news.title) != null
    }

    fun savingMethod(news: News): Boolean {
        val saved = checkIfSaved(news = news)
        if (saved) {
            locale.unSaveNews(news = news)
        } else {
            locale.saveNews(news = news)
        }
        return !saved
    }
}