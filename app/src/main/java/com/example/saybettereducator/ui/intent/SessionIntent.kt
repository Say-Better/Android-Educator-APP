package com.example.saybettereducator.ui.intent

sealed class SessionIntent {
    object OnRemoteViewReady: SessionIntent()
    object StartProgress : SessionIntent() // 추가된 Intent
    object HelloClicked : SessionIntent() // 추가된 Intent
}