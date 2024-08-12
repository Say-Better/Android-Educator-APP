package com.example.saybettereducator.ui.intent

import com.example.saybettereducator.data.model.Symbol
import com.example.saybettereducator.ui.model.ResponseFilterType

sealed class ProgressIntent {
    data object LoadSymbols : ProgressIntent()
    data class SelectMode(val mode: Int) : ProgressIntent()
    data class SymbolClicked(val symbol: Symbol?) : ProgressIntent()
    data class SelectSymbol(val symbol: Symbol) : ProgressIntent()
    data class DeselectSymbol(val symbol: Symbol) : ProgressIntent()
    data object AddSymbolClicked : ProgressIntent()
    data class StartVoicePlayback(val symbol: Symbol) : ProgressIntent()
    data object StopVoicePlayback : ProgressIntent()
    data object ToggleBottomSheet : ProgressIntent()
    data class ApplyResponseFilter(val filterType: ResponseFilterType) : ProgressIntent()
    data object CommunicationClicked : ProgressIntent()
    data object TimerClicked : ProgressIntent()
}

