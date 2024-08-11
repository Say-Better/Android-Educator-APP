package com.example.saybettereducator.ui.viewmodel

import com.example.saybettereducator.R
import com.example.saybettereducator.domain.model.Symbol
import com.example.saybettereducator.ui.common.MviViewModel
import com.example.saybettereducator.ui.intent.ProgressIntent
import com.example.saybettereducator.ui.model.ProgressState
import com.example.saybettereducator.ui.sideeffect.ProgressSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(
) : MviViewModel<ProgressState, ProgressSideEffect, ProgressIntent>(ProgressState()) {
    override fun handleIntent(intent: ProgressIntent) {
        when (intent) {
            is ProgressIntent.LoadSymbols -> loadSymbols()
            is ProgressIntent.SelectMode -> selectMode(intent.mode)
            is ProgressIntent.AddSymbolClicked -> {}
            is ProgressIntent.SymbolClicked -> {}
        }
    }

    private val _state = MutableStateFlow(ProgressState())
    val state: StateFlow<ProgressState> = _state

    init {
        loadSymbols()
    }

    private fun loadSymbols() {
        val symbols = listOf(
            Symbol(1, "Symbol 1", R.drawable.symbol_go),
            Symbol(2, "Symbol 2", R.drawable.symbol_shy),
            Symbol(3, "Symbol 3", R.drawable.symbol_rice),
            Symbol(4, "Symbol 4", R.drawable.symbol_hello),
            Symbol(5, "Symbol 5", R.drawable.symbol_hungry),
            // Add more symbols as needed
        )
        _state.update { it.copy(symbols = symbols) }
    }

    fun selectMode(mode: Int) {
        _state.update { it.copy(selectedMode = mode) }
    }
}
