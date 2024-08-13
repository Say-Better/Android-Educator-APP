package com.example.saybettereducator.ui.intent

import com.example.saybettereducator.ui.sideeffect.VideoCallSideEffect

sealed class VideoCallIntent {
    object OnRemoteViewReady: VideoCallIntent()
    object SetupView: VideoCallIntent()
    data class SendRTCMessage(val msg: String) : VideoCallIntent()
}