package com.example.saybettereducator.ui.sideeffect

sealed class SessionSideEffect {
    data object PeerConnectionSuccess : SessionSideEffect()
}