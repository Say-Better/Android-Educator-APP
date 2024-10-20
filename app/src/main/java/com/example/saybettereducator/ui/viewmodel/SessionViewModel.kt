package com.example.saybettereducator.ui.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.saybettereducator.data.repository.MainRepository
import com.example.saybettereducator.data.service.MainService
import com.example.saybettereducator.ui.common.MviViewModel
import com.example.saybettereducator.ui.intent.SessionIntent
import com.example.saybettereducator.ui.model.SessionState
import com.example.saybettereducator.ui.sideeffect.SessionSideEffect
import com.example.saybettereducator.utils.InstantInteractionType.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val mainRepository: MainRepository
): MviViewModel<SessionState, SessionSideEffect, SessionIntent>(SessionState()), MainRepository.ConnectionListener, MainService.SessionInteractionListener {

    override fun handleIntent(intent: SessionIntent) {
        when (intent) {
            is SessionIntent.OnRemoteViewReady -> onRemoteViewReady()
            is SessionIntent.StartProgress -> startProgress()
            is SessionIntent.HelloClicked -> onHelloClicked()
            is SessionIntent.SetScreenShare -> onScreenShareClicked(intent.isScreenCasting)
            is SessionIntent.EndingProgress -> endingProgress()
            is SessionIntent.StartScreenShare -> onStartScreenShare()
            is SessionIntent.StopScreenShare -> onStopScreenShare()
        }
    }

    private fun onStartScreenShare() {
        // 화면공유 시작
        startScreenCapture()
    }

    private fun startScreenCapture() {

    }

    private fun onStopScreenShare() {
        // 화면공유 종료

    }

    init {
        mainRepository.connectionListener = this
        MainService.sessionInteractionListener = this
    }

    private fun onHelloClicked() {
        mainRepository.sendTextToDataChannel(GREETING.name)
        updateState { it.copy(greetState = true) }
        viewModelScope.launch {
            delay(1000) // 3초 동안 표시
            updateState { it.copy(greetState = false) }
        }
    }

    private fun onScreenShareClicked(screenCasting: Boolean) {
        updateState { it.copy(isScreenCasting = screenCasting) }
    }

    private fun onRemoteViewReady() {
        updateState { it.copy(isPeerConnected = true) }
    }

    override fun onPeerConnectionReady() {
        Log.d("VideoCallViewModel", "Remote View Ready")
        postSideEffect(SessionSideEffect.PeerConnectionSuccess)
    }

    private fun startProgress() {
        updateState { it.copy(isStart = true) }

        //peer에게 Learning 모드로 전환 요청
        mainRepository.sendTextToDataChannel(SWITCH_TO_LEARNING.name)
    }

    private fun endingProgress() {
        updateState { it.copy(isEnding = true) }

        //peer에게 Ending 모드로 전환 요청
        mainRepository.sendTextToDataChannel(SWITCH_TO_ENDING.name)
    }

    override fun onReceiveChatting(msg: String) {
        // text 레이블 상태 업데이트
        updateState { it.copy(longChatText = it.longChatText + "\n" + msg) }
    }


    override fun onGreeting() {
        updateState { it.copy(remoteGreetState = true) }
        viewModelScope.launch {
            delay(1000) // 3초 동안 표시
            updateState { it.copy(remoteGreetState = false) }
        }
    }

    override fun onSwitchToLearning() {

    }
}