package com.example.saybettereducator.ui.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.saybettereducator.data.repository.MainRepository
import com.example.saybettereducator.ui.common.MviViewModel
import com.example.saybettereducator.ui.intent.SessionIntent
import com.example.saybettereducator.ui.model.SessionState
import com.example.saybettereducator.ui.sideeffect.SessionSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val mainRepository: MainRepository
): MviViewModel<SessionState, SessionSideEffect, SessionIntent>(SessionState()), MainRepository.ConnectionListener {

    override fun handleIntent(intent: SessionIntent) {
        when (intent) {
            is SessionIntent.OnRemoteViewReady -> onRemoteViewReady()
            is SessionIntent.SetupView -> setupView()
            is SessionIntent.SendRTCMessage -> sendRTCMessage(intent.msg)
            is SessionIntent.StartSession -> startSession()
            is SessionIntent.HelloClicked -> onHelloClicked()
        }
    }

    private fun sendRTCMessage(msg: String) {
        mainRepository.sendToDataChannel(msg)
    }

    init {
        mainRepository.connectionListener = this
    }

    private fun setupView() {

    }

    private fun onHelloClicked() {
        updateState { it.copy(greetState = true) }
        viewModelScope.launch {
            delay(1000) // 3초 동안 표시
            updateState { it.copy(greetState = false) }
        }
    }

    private fun onRemoteViewReady() {
        updateState { it.copy(isPeerConnected = true) }
    }

    override fun onPeerConnectionReady() {
        Log.d("VideoCallViewModel", "Remote View Ready")
        postSideEffect(SessionSideEffect.PeerConnectionSuccess)
    }

    private fun startSession() {
        updateState { it.copy(isStart = true) }
    }
}