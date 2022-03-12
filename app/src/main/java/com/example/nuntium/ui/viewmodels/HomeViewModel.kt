package com.example.nuntium.ui.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nuntium.data.locale.News
import com.example.nuntium.domain.locale.RoomRepository
import com.example.nuntium.domain.pagination.MyPaginator
import com.example.nuntium.domain.remote.ApiRepository
import com.example.nuntium.ui.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val remote: ApiRepository,
    val locale: RoomRepository
) : ViewModel() {
    lateinit var topicNewsPaginator: MyPaginator
    val tabResults = mutableStateOf<Resource<List<News>>>(Resource.Loading())
    val recommendedResult = mutableStateOf<Resource<List<News>>>(Resource.Loading())
    val searchResult = mutableStateOf<Resource<List<News>>>(Resource.Loading())

    fun onTabItemChanged(index: Int, topic: String) {
        viewModelScope.launch {
            if (index == 0) {
                topicNewsPaginator = MyPaginator(remote, MyPaginator.MethodType.TopHeadlines)
            } else {
                topicNewsPaginator = MyPaginator(remote, MyPaginator.MethodType.Search)
            }
            topicNewsPaginator.query = topic
            topicNewsPaginator.handlePageChange()
            topicNewsPaginator.response.collectLatest {
                tabResults.value = it
            }
        }
    }

    fun onTabListScrollChanged(position: Int) {
        topicNewsPaginator.onScrollPositionChanged(position = position)
    }
}
