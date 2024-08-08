package com.example.saybettereducator.ui.common

interface IntentHandler<INTENT: Any> {
    fun handleIntent(intent: INTENT)
}