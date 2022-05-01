package com.example.nuntium.ui.mainAppPage

sealed class MainAppScreenStates {
    object HomePage : MainAppScreenStates()
    object TopicPickPage : MainAppScreenStates()
    object SavedNewsPage : MainAppScreenStates()
    object ProfilePage : MainAppScreenStates()
}