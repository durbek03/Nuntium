package com.example.nuntium.ui.homePage.intent

sealed class HomePageIntent {
    object ResetTopicNewsRow : HomePageIntent()
    object OpenSearchPage : HomePageIntent()
    object OpenCasualPage : HomePageIntent()
}