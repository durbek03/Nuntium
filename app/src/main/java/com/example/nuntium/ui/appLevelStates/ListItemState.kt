package com.example.nuntium.ui.appLevelStates

sealed class ListItemState<T>(val data: T?) {
    class LoadedItemState<T>(val item: T) : ListItemState<T>(item)
    class LoadingItemState<T> : ListItemState<T>(null)
}