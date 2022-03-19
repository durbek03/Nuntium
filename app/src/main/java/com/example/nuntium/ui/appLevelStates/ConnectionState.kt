package com.example.nuntium.ui.appLevelStates

sealed class ConnectionState {
    object ConnectionOn : ConnectionState()
    object ConnectionOff : ConnectionState()
}