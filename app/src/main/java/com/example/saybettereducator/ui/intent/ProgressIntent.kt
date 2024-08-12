package com.example.saybettereducator.ui.intent

import com.example.saybettereducator.domain.model.Symbol

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
}
