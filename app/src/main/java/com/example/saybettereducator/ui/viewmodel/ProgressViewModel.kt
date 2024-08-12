package com.example.saybettereducator.ui.viewmodel

import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.saybettereducator.R
import com.example.saybettereducator.domain.model.Symbol
import com.example.saybettereducator.ui.common.MviViewModel
import com.example.saybettereducator.ui.intent.ProgressIntent
import com.example.saybettereducator.ui.model.CommunicationType
import com.example.saybettereducator.ui.model.ProgressState
import com.example.saybettereducator.ui.model.ResponseFilterType
import com.example.saybettereducator.ui.sideeffect.ProgressSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val textToSpeech: TextToSpeech
) : MviViewModel<ProgressState, ProgressSideEffect, ProgressIntent>(ProgressState()) {
    private var timerJob: Job? = null

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
            is ProgressIntent.ApplyResponseFilter -> applyResponseFilter(intent.filterType)
            is ProgressIntent.CommunicationClicked -> handleCommunicationClicked()
            is ProgressIntent.TimerClicked -> handleTimerClicked()
        }
    }

    init {
        loadSymbols()
        initTimerMaxTime(10000)

        textToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {}
            override fun onDone(utteranceId: String?) {
                Log.d("ProgressViewModel", "Voice playback completed")
                stopVoicePlayback()
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

    private fun initTimerMaxTime(maxTime: Long) {
        updateState { it.copy(timerMaxTime = maxTime, timerTime = maxTime) }
    }

    private fun handleCommunicationClicked() {
        if (container.stateFlow.value.communicationState == CommunicationType.NotCommunicating) {
            updateState {
                it.copy(
                    communicationState = CommunicationType.Communicating,
                    timerTime = it.timerMaxTime
                )
            }
            setTimer()
        }
        else stopCommunication()
    }

    private fun handleTimerClicked() {
        when (container.stateFlow.value.communicationState) {
            CommunicationType.NotCommunicating -> {}
            CommunicationType.Paused -> {
                updateState { it.copy(communicationState = CommunicationType.Communicating) }
                setTimer()
            }
            CommunicationType.Communicating -> {
                timerJob?.cancel()
                updateState { it.copy(communicationState = CommunicationType.Paused) }
            }
        }
    }

    private fun setTimer() {
        timerJob = viewModelScope.launch {
            while (container.stateFlow.value.timerTime > 0) {
                delay(1000)
                updateState { it.copy(timerTime = it.timerTime - 1000) }
            }
            stopCommunication()
        }
    }

    private fun stopCommunication() {
        timerJob?.cancel()
        updateState {
            it.copy(
                communicationState = CommunicationType.NotCommunicating,
                timerTime = it.timerMaxTime
            )
        }
    }

    private fun onTimerFinished() {
        stopCommunication()
        updateState { it.copy(communicationCount = it.communicationCount + 1) }
    }

    private fun handleSymbolClicked(symbol: Symbol?) {
        val currentState = container.stateFlow.value
        when {
            symbol == null -> { toggleBottomSheet() }
            currentState.playingSymbol == symbol -> { stopVoicePlayback() }
            else -> { startVoicePlayback(symbol) }
        }
    }

    private fun startVoicePlayback(symbol: Symbol) {
        val utteranceId = symbol.id.toString() // 각 심볼의 ID를 사용하여 고유한 utteranceId 설정

        updateState {
            it.copy(isVoicePlaying = true, playingSymbol = symbol)
        }

        textToSpeech.speak(symbol.name, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
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

    private fun applyResponseFilter(filterType: ResponseFilterType) {
        updateState { it.copy(responseFilter = filterType) }

        // 3초 후에 필터를 자동으로 제거
        viewModelScope.launch {
            delay(3000) // 3초 대기
            updateState { it.copy(responseFilter = ResponseFilterType.NONE) }
        }
    }
}
