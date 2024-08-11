package com.example.saybettereducator.ui.intent

import com.example.saybettereducator.domain.model.SymbolCard

sealed class ProgressIntent {
    object LoadSymbols : ProgressIntent()
    data class SelectMode(val mode: Int) : ProgressIntent()
    data class SymbolClicked(val symbol: SymbolCard) : ProgressIntent()
    object AddSymbolClicked : ProgressIntent()
}
