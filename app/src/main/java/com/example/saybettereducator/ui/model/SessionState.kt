package com.example.saybettereducator.ui.model

data class SessionState(
    val isPeerConnected: Boolean = false,
    val isCaller: Boolean = true,
    var target: String? = null,
    val isStart: Boolean = false,
    val greetState: Boolean = false,
    val remoteGreetState: Boolean = false
)
