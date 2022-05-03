package com.example.nuntium.ui.homePage.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nuntium.constants.Constants
import com.example.nuntium.data.locale.AppDatabase
import com.example.nuntium.data.locale.News
import com.example.nuntium.domain.remote.ApiRepository
import com.example.nuntium.ui.appLevelStates.ListItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val locale: AppDatabase,
    val apiRepository: ApiRepository
) : ViewModel() {
    val query = MutableStateFlow<String>("")
    val loadingDummyItems = Constants.loadingDummy
    val page = MutableSharedFlow<Int>()
    var currentPage = 1
    val response = MutableStateFlow<List<ListItemState<News>>>(emptyList())
    var loading = false
    val scrollIndex = MutableSharedFlow<Int>()

    private val language = MutableStateFlow<String>("en")

    init {
        handleLanguageChange()
        getAppLanguage()
        handlePageChange()
        handleQueryChange()
        handleScrollIndex()
    }

    private fun handleLanguageChange() {
        viewModelScope.launch {
            language.collectLatest {
                loading = false
                currentPage = 1
                response.value = emptyList()
                page.emit(1)
            }
        }
    }

    private fun getAppLanguage() {
        viewModelScope.launch(Dispatchers.IO) {
            locale.appLanguageDao().getLanguage().collectLatest {
                var lan: String = "en"
                if (it.isNotEmpty()) {
                    lan = it[0].language
                }
                language.emit(lan)
            }
        }
    }

    private fun handleScrollIndex() {
        viewModelScope.launch {
            scrollIndex.collect {
                Log.d("SearchViewModel", "handleScrollIndex: $currentPage")
                if (it + 2 >= currentPage * 20) {
                    if (!loading) {
                        page.emit(currentPage + 1)
                    }
                }
            }
        }
    }

    private fun handleQueryChange() {
        viewModelScope.launch {
            query.collectLatest {
                loading = false
                currentPage = 1
                response.value = emptyList()
                page.emit(1)
            }
        }
    }

    private fun handlePageChange() {
        viewModelScope.launch(Dispatchers.IO) {
            page.collectLatest {
                currentPage = it
                emitLoading()
                loading = true
                try {
                    val news = apiRepository.getNews(currentPage, query = query.value, language = language.value)
                    emitLoaded(news)
                    loading = false
                } catch (e: Exception) {
                    loading = false
                    emitLoaded(emptyList())
                }
            }
        }
    }

    private fun emitLoaded(data: List<News>) {
        val result = data.map { ListItemState.LoadedItemState<News>(it) }
        val oldList = response.value.filter { it is ListItemState.LoadedItemState }
        viewModelScope.launch {
            response.emit(oldList.toMutableList().also { list -> list.addAll(result) })
        }
    }

    private fun emitLoading() {
        viewModelScope.launch {
            response.emit(response.value.toMutableList().also { list ->
                list.addAll(loadingDummyItems)
            })
        }
    }
}