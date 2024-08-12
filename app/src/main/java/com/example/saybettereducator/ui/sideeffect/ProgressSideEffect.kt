package com.example.saybettereducator.ui.sideeffect

sealed class ProgressSideEffect {
    object OpenBottomSheet : ProgressSideEffect()
    object CloseBottomSheet : ProgressSideEffect()
}