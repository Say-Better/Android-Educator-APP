package com.example.saybettereducator.ui.model

import com.example.saybettereducator.domain.model.Symbol
import com.example.saybettereducator.ui.intent.ResponseFilterType

data class ProgressState(
    val selectedMode: Int = 1,
    val symbols: List<Symbol> = emptyList(),
    val selectedSymbols: List<Symbol> = emptyList(),
    val isVoicePlaying: Boolean = false,
    val playingSymbol: Symbol? = null,
    val isBottomSheetOpen: Boolean = false,
    val responseFilter: ResponseFilterType = ResponseFilterType.NONE
)
