package com.example.saybettereducator.ui.sideeffect

sealed class UserInfoSideEffect {
    object NavigateHome : UserInfoSideEffect()
    object NavigateLoginSuccess : UserInfoSideEffect()
    object NetworkError : UserInfoSideEffect()
}