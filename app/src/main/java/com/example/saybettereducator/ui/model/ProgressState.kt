package com.example.saybettereducator.ui.model

import com.example.saybettereducator.domain.model.Symbol

data class ProgressState(
    val selectedMode: Int = 1,
    val symbols: List<Symbol> = emptyList(),
    val selectedSymbols: List<Symbol> = emptyList()
)
