package com.example.nuntium

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nuntium.constants.AppThemeMode
import com.example.nuntium.data.locale.AppDatabase
import com.example.nuntium.data.locale.AppThemeModeRoom
import com.example.nuntium.data.locale.News
import com.example.nuntium.domain.locale.RoomRepository
import com.example.nuntium.sharedPreferences.MySharedPreferences
import com.example.nuntium.ui.mainAppPage.MainAppScreenStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val locale: RoomRepository,
    val sharedPreferences: MySharedPreferences,
    val room: AppDatabase
) : ViewModel() {
    private val TAG = "MainViewModel"
    val mainAppScreenState = MutableStateFlow<MainAppScreenStates>(MainAppScreenStates.HomePage)
    val columnState: LazyListState = LazyListState()
    val topicListState: LazyListState = LazyListState()
    val topicNewsListState: LazyListState = LazyListState()
    val savedNewsListState: LazyListState = LazyListState()

    val appThemeMode: MutableStateFlow<AppThemeMode> =
        MutableStateFlow<AppThemeMode>(AppThemeMode.LightMode)

    init {
        loadSavedNews()
        getAppThemeMode()
    }

    val savedNews = MutableStateFlow<List<News>>(emptyList())

    private fun getAppThemeMode() {
        viewModelScope.launch(Dispatchers.IO) {
            room.appThemeDao().getThemeMode().collectLatest {
                if (it.isEmpty()) return@collectLatest
                if (it[0].isDarkMode) {
                    appThemeMode.emit(AppThemeMode.DarkMode)
                } else {
                    appThemeMode.emit(AppThemeMode.LightMode)
                }
            }
        }
    }

    fun changeAppThemeMode(darkModeActivate: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            room.appThemeDao().dropTable()
            if (darkModeActivate) {
                room.appThemeDao().changeTheme(AppThemeModeRoom(null, true))
            } else {
                room.appThemeDao().changeTheme(AppThemeModeRoom(null, false))
            }
        }
    }

    private fun loadSavedNews() {
        viewModelScope.launch(Dispatchers.IO) {
            locale.getSavedNews().collectLatest {
                Log.d(TAG, "loadSavedNews: change detected: ${it.toString()}")
                savedNews.emit(it)
            }
        }
    }

    fun isFirstLaunch(): Boolean {
        return sharedPreferences.isFirstLaunch
    }

    fun firstLaunched() {
        sharedPreferences.isFirstLaunch = false
    }
}