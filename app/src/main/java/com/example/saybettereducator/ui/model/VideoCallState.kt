package com.example.saybettereducator.ui.model

data class VideoCallState(
    val isPeerConnected: Boolean = false,
    val isCaller: Boolean = true,
    var target: String? = null
)
