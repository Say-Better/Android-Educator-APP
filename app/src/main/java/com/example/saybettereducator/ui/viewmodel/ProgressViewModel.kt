package com.example.saybettereducator.ui.viewmodel

import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.saybettereducator.R
import com.example.saybettereducator.data.model.Symbol
import com.example.saybettereducator.data.repository.MainRepository
import com.example.saybettereducator.data.service.MainService
import com.example.saybettereducator.ui.common.MviViewModel
import com.example.saybettereducator.ui.intent.ProgressIntent
import com.example.saybettereducator.ui.model.CommunicationType
import com.example.saybettereducator.ui.model.ProgressState
import com.example.saybettereducator.ui.model.ResponseFilterType
import com.example.saybettereducator.ui.sideeffect.ProgressSideEffect
import com.example.saybettereducator.utils.InstantInteractionType
import com.example.saybettereducator.utils.InstantInteractionType.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProgressViewModel @Inject constructor(
    private val textToSpeech: TextToSpeech
) : MviViewModel<ProgressState, ProgressSideEffect, ProgressIntent>(ProgressState()), MainService.ProgressInteractionListener {
    private var timerJob: Job? = null

    @Inject lateinit var mainRepository : MainRepository

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
        MainService.progressInteractionListener = this

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
        val symbols = listOf(
            Symbol(0, "가요", R.drawable.ic_symbol_go),
            Symbol(1, "감격하다", R.drawable.ic_symbol_moved),
            Symbol(2, "놀라다", R.drawable.ic_symbol_surprised),
            Symbol(3, "따분하다", R.drawable.ic_symbol_boring),
            Symbol(4, "떨리다", R.drawable.ic_symbol_tremble),
            Symbol(5, "미안하다", R.drawable.ic_symbol_sorry),
            Symbol(6, "민망하다", R.drawable.ic_symbol_embarrassed),
            Symbol(7, "반가워요", R.drawable.ic_symbol_glad),
            Symbol(8, "밥", R.drawable.ic_symbol_rice),
            Symbol(9, "배고파요", R.drawable.ic_symbol_hungry),
            Symbol(10, "부끄러워요", R.drawable.ic_symbol_shy),
            Symbol(11, "부담스럽다", R.drawable.ic_symbol_pressured),
            Symbol(12, "부럽다", R.drawable.ic_symbol_envious),
            Symbol(13, "뿌듯하다", R.drawable.ic_symbol_plessure),
            Symbol(14, "상처받다", R.drawable.ic_symbol_hurt),
            Symbol(15, "속상해요", R.drawable.ic_symbol_upset),
            Symbol(16, "시작", R.drawable.ic_symbol_start),
            Symbol(17, "신나요", R.drawable.ic_symbol_excited),
            Symbol(18, "어리둥절하다", R.drawable.ic_symbol_confused),
            Symbol(19, "우울해요", R.drawable.ic_symbol_depressed),
            Symbol(20, "자랑스럽다", R.drawable.ic_symbol_proud),
            Symbol(21, "짜증나다", R.drawable.ic_symbol_annoying),
            Symbol(22, "화나요", R.drawable.ic_symbol_angry),
            Symbol(23, "흥미롭다", R.drawable.ic_symbol_interested),
        )
        updateState { it.copy(symbols = symbols) }
    }

    private fun selectMode(mode: Int) {
        updateState { it.copy(selectedMode = mode) }
        when(mode){
            // 1 view
            1 -> {
                mainRepository.sendTextToDataChannel(SWITCH_TO_LAYOUT_1.name)
            }

            // 2 view
            2 -> {
                mainRepository.sendTextToDataChannel(SWITCH_TO_LAYOUT_2.name)
            }

            // 4 view
            3 -> {
                mainRepository.sendTextToDataChannel(SWITCH_TO_LAYOUT_4.name)
            }

            // all view
            4 -> {
                mainRepository.sendTextToDataChannel(SWITCH_TO_LAYOUT_ALL.name)
            }
        }
    }

    private fun selectSymbol(symbol: Symbol) {
        updateState { state ->
            val newSelectedSymbols = state.selectedSymbols + symbol
            Log.d("ProgressViewModel", "Symbol selected: $symbol, updated selectedSymbols: $newSelectedSymbols")
            state.copy(selectedSymbols = newSelectedSymbols)
        }

        mainRepository.sendTextToDataChannel("${SYMBOL_SELECT.name} ${symbol.id}")
    }

    private fun deselectSymbol(symbol: Symbol) {
        updateState { state ->
            val newSelectedSymbols = state.selectedSymbols - symbol
            Log.d("ProgressViewModel", "Symbol deselected: $symbol, updated selectedSymbols: $newSelectedSymbols")
            state.copy(selectedSymbols = newSelectedSymbols)
        }

        mainRepository.sendTextToDataChannel("${SYMBOL_DELETE.name} ${symbol.id}")
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
//            currentState.playingSymbol == symbol -> { stopVoicePlayback() }
            else -> viewModelScope.launch {
                mainRepository.sendTextToDataChannel("${SYMBOL_HIGHLIGHT.name} ${symbol.id}")
                startVoicePlayback(symbol)
                delay(2000)
                stopVoicePlayback()
            }
        }
    }

    private fun startVoicePlayback(symbol: Symbol) {
        val utteranceId = symbol.id.toString() // 각 심볼의 ID를 사용하여 고유한 utteranceId 설정

        updateState {
            it.copy(isVoicePlaying = true, playingSymbol = symbol)
        }

        textToSpeech.speak(symbol.name, TextToSpeech.QUEUE_ADD, null, utteranceId)
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

    override fun onSymbolHighlight(symbolId: Int) {
        val currentState = container.stateFlow.value
        val symbol: Symbol = currentState.symbols[symbolId]
        viewModelScope.launch {
            startVoicePlayback(symbol)
            delay(2000)
            stopVoicePlayback()
        }

    }
}
