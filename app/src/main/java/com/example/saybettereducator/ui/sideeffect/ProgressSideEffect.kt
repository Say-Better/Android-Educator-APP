package com.example.saybettereducator.ui.sideeffect

sealed class ProgressSideEffect {
    object NavigateHome : ProgressSideEffect()
    object NavigateLoginSuccess : ProgressSideEffect()
    object NetworkError : ProgressSideEffect()
}