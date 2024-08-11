package com.example.saybettereducator.ui.model

import com.example.saybettereducator.domain.model.SymbolCard

data class ProgressState(
    val selectedMode: Int = 1,
    val symbols: List<SymbolCard> = emptyList()
)
