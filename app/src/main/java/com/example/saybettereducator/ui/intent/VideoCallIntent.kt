package com.example.saybettereducator.ui.intent

sealed class VideoCallIntent {
    object OnRemoteViewReady: VideoCallIntent()
    object SetupView: VideoCallIntent()
}