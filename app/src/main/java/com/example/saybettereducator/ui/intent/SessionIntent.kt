package com.example.saybettereducator.ui.intent

sealed class SessionIntent {
    data object OnRemoteViewReady: SessionIntent()
    data object StartProgress : SessionIntent() // 추가된 Intent
    data class ScreenShareClicked(val isScreenCasting: Boolean) : SessionIntent()
    data object EndingProgress : SessionIntent()
    data object HelloClicked : SessionIntent() // 추가된 Intent
}