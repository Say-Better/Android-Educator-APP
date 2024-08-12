package com.example.saybettereducator.ui.sideeffect

sealed class VideoCallSideEffect {
    object PeerConnectionSuccess : VideoCallSideEffect()
}