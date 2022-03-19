package com.example.nuntium.ui.homePage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nuntium.constants.Constants
import com.example.nuntium.data.locale.News
import com.example.nuntium.data.remote.ApiService
import com.example.nuntium.domain.remote.ApiRepository
import com.example.nuntium.ui.appLevelStates.ListItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val apiRepository: ApiRepository
) : ViewModel() {
    val query = MutableStateFlow<String>("")
    val loadingDummyItems = Constants.loadingDummy
    val page = MutableSharedFlow<Int>()
    var currentPage = 1
    val isScrolledToEnd = MutableSharedFlow<Boolean>()
    val response = MutableStateFlow<List<ListItemState<News>>>(emptyList())

    init {
        handlePageChange()
        handleScrollState()
        handleQueryChange()
    }

    private fun handleQueryChange() {
        viewModelScope.launch {
            currentPage = 1
            response.value = emptyList()
            page.emit(1)
        }
    }

    private fun handleScrollState() {
        viewModelScope.launch {
            isScrolledToEnd.collectLatest {
                page.emit(currentPage + 1)
            }
        }
    }

    private fun handlePageChange() {
        viewModelScope.launch(Dispatchers.IO) {
            page.collectLatest {
                if (response.value.contains(ListItemState.LoadingItemState()) || query.value.isEmpty()) {
                    return@collectLatest
                }
                emitLoading()
                currentPage = it
                try {
                    val news = apiRepository.getNews(currentPage, query = query.value)
                    emitLoaded(news)
                } catch (e: Exception) {
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