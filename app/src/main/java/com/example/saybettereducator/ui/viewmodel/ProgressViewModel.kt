package com.example.saybettereducator.ui.viewmodel

import android.util.Log
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
            is ProgressIntent.SelectSymbol -> {
                selectSymbol(intent.symbol)
            }
            is ProgressIntent.DeselectSymbol -> {
                deselectSymbol(intent.symbol)
            }
        }
    }

    init {
        loadSymbols()
    }

    private fun loadSymbols() {
        val symbols = List(20) { i ->
            Symbol(i + 1, "Symbol ${i + 1}", R.drawable.symbol_go)
        }
        updateState { it.copy(symbols = symbols) }
    }

    private fun selectMode(mode: Int) {
        updateState { it.copy(selectedMode = mode) }
    }

    private fun selectSymbol(symbol: Symbol) {
        updateState { state ->
            val newSelectedSymbols = state.selectedSymbols + symbol
            Log.d("ProgressViewModel", "Symbol selected: $symbol, updated selectedSymbols: $newSelectedSymbols")
            state.copy(selectedSymbols = newSelectedSymbols)
        }
    }

    private fun deselectSymbol(symbol: Symbol) {
        updateState { state ->
            val newSelectedSymbols = state.selectedSymbols - symbol
            Log.d("ProgressViewModel", "Symbol deselected: $symbol, updated selectedSymbols: $newSelectedSymbols")
            state.copy(selectedSymbols = newSelectedSymbols)
        }
    }

}
