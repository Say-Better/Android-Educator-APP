package com.example.saybettereducator.ui.viewmodel

import android.content.Context
import android.os.Build
import android.provider.MediaStore.Video
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.saybettereducator.data.repository.MainRepository
import com.example.saybettereducator.data.repository.MainServiceRepository
import com.example.saybettereducator.data.service.MainService
import com.example.saybettereducator.ui.common.MviViewModel
import com.example.saybettereducator.ui.intent.VideoCallIntent
import com.example.saybettereducator.ui.model.VideoCallState
import com.example.saybettereducator.ui.sideeffect.VideoCallSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import org.webrtc.SurfaceViewRenderer
import javax.inject.Inject

@HiltViewModel
class VideoCallViewModel @Inject constructor(
    private val mainRepository: MainRepository
): MviViewModel<VideoCallState, VideoCallSideEffect, VideoCallIntent>(VideoCallState()), MainRepository.ConnectionListener {

    override fun handleIntent(intent: VideoCallIntent) {
        when (intent) {
            is VideoCallIntent.OnRemoteViewReady -> onRemoteViewReady()
            is VideoCallIntent.SetupView -> setupView()
        }
    }

    init {
        mainRepository.connectionListener = this
    }

    private fun setupView() {

    }

    private fun onRemoteViewReady() {
        updateState { it.copy(isPeerConnected = true) }
    }

    override fun onPeerConnectionReady() {
        Log.d("VideoCallViewModel", "Remote View Ready")
        postSideEffect(VideoCallSideEffect.PeerConnectionSuccess)
    }
}