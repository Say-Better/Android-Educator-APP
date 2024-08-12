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
            is ProgressIntent.SelectSymbol -> selectSymbol(intent.symbol)
            is ProgressIntent.DeselectSymbol -> deselectSymbol(intent.symbol)
            is ProgressIntent.SymbolClicked -> handleSymbolClicked(intent.symbol)
            is ProgressIntent.StartVoicePlayback -> startVoicePlayback(intent.symbol)
            is ProgressIntent.StopVoicePlayback -> stopVoicePlayback()
            is ProgressIntent.OpenBottomSheet -> openBottomSheet()
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

    private fun handleSymbolClicked(symbol: Symbol?) {
        val currentState = container.stateFlow.value
        when {
            symbol == null -> openBottomSheet()
            currentState.playingSymbol == symbol -> stopVoicePlayback()
            else -> startVoicePlayback(symbol)
        }
    }

    private fun startVoicePlayback(symbol: Symbol) {
        updateState {
            it.copy(isVoicePlaying = true, playingSymbol = symbol)
        }
        // 여기에 음성 재생 로직 추가
        // 예: TTS 또는 미디어 플레이어를 통해 재생
        Log.d("ProgressViewModel", "Voice playback started for symbol: $symbol")
    }

    private fun stopVoicePlayback() {
        updateState {
            it.copy(isVoicePlaying = false, playingSymbol = null)
        }
        // 여기에 음성 중지 로직 추가
        Log.d("ProgressViewModel", "Voice playback stopped")
    }

    private fun openBottomSheet() {
        // 여기에 바텀시트를 여는 로직을 추가 (상태 업데이트, 사이드 이펙트 등)
        Log.d("ProgressViewModel", "Bottom sheet opened")
    }
}
