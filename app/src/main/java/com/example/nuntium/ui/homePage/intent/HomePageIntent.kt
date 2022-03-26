package com.example.nuntium.ui.homePage.intent

sealed class HomePageIntent {
    object OpenSearchPage : HomePageIntent()
    object OpenCasualPage : HomePageIntent()
}