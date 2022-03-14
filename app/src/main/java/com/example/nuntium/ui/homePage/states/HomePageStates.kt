package com.example.nuntium.ui.homePage.states

sealed class HomePageStates {
    object SearchOn : HomePageStates()
    object CasualPage : HomePageStates()
}