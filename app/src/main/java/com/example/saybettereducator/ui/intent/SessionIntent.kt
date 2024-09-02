package com.example.saybettereducator.ui.intent

sealed class SessionIntent {
    object OnRemoteViewReady: SessionIntent()
    object SetupView: SessionIntent()
    data class SendRTCMessage(val msg: String) : SessionIntent()
    object StartSession : SessionIntent() // 추가된 Intent
    object HelloClicked : SessionIntent() // 추가된 Intent
}