package com.example.saybettereducator.ui.viewmodel

import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import com.example.saybettereducator.R
import com.example.saybettereducator.domain.model.Symbol
import com.example.saybettereducator.ui.common.MviViewModel
import com.example.saybettereducator.ui.intent.ProgressIntent
import com.example.saybettereducator.ui.model.ProgressState
import com.example.saybettereducator.ui.sideeffect.ProgressSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val textToSpeech: TextToSpeech
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
            is ProgressIntent.ToggleBottomSheet -> toggleBottomSheet()
        }
    }

    init {
        loadSymbols()

        textToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {}
            override fun onDone(utteranceId: String?) {
                stopVoicePlayback()
                Log.d("ProgressViewModel", "Voice playback completed")
            }
            override fun onError(utteranceId: String?) {
                Log.e("ProgressViewModel", "Voice playback error")
            }
        })
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
            symbol == null -> {
                toggleBottomSheet()
            }
            currentState.playingSymbol == symbol -> {
                stopVoicePlayback()
            }
            else -> {
                startVoicePlayback(symbol)
            }
        }
    }

    private fun startVoicePlayback(symbol: Symbol) {
        updateState {
            it.copy(isVoicePlaying = true, playingSymbol = symbol)
        }

        textToSpeech.speak(symbol.name, TextToSpeech.QUEUE_FLUSH, null, null)
        Log.d("ProgressViewModel", "Voice playback started for symbol: $symbol")
    }

    private fun stopVoicePlayback() {
        textToSpeech.stop()
        updateState {
            it.copy(isVoicePlaying = false, playingSymbol = null)
        }
        Log.d("ProgressViewModel", "Voice playback stopped")
    }

    private fun toggleBottomSheet() {
        val currentState = container.stateFlow.value
        val sideEffect = if (currentState.isBottomSheetOpen) {
            ProgressSideEffect.CloseBottomSheet
        } else {
            ProgressSideEffect.OpenBottomSheet
        }
        postSideEffect(sideEffect)
        updateState { it.copy(isBottomSheetOpen = !currentState.isBottomSheetOpen) }
    }

    override fun onCleared() {
        super.onCleared()
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}
