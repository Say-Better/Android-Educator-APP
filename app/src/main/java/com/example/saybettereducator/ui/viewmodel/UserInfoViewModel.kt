package com.example.saybettereducator.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.saybettereducator.ui.intent.UserInfoIntent
import com.example.saybettereducator.ui.model.UserInfoState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@HiltViewModel
class UserInfoViewModel : ViewModel() {
    private val _state = MutableStateFlow(UserInfoState())
    val state: MutableStateFlow<UserInfoState> get() = _state

    fun processIntent(intent: UserInfoIntent) {
        when (intent) {
            is UserInfoIntent.Refresh -> loadProfile()
            is UserInfoIntent.UpdateProfileImage -> updateProfileImage(intent.uri)
            is UserInfoIntent.UpdateName -> updateName(intent.name)
            is UserInfoIntent.UpdateShowPopup -> showPopup(intent.showPopup)
            is UserInfoIntent.UpdateOpenCamera -> openCamera(intent.openCamera)
            is UserInfoIntent.UpdateOpenGallery -> openGallery(intent.openGallery)
        }
    }

    private fun loadProfile() {
        TODO("Not yet implemented")
    }

    private fun updateProfileImage(uri: Uri) {
        _state.value = _state.value.copy(profileImageUrl = uri)
    }

    private fun updateName(name: String) {
        _state.value = _state.value.copy(name = name)
    }

    private fun showPopup(show: Boolean) {
        _state.value = _state.value.copy(showPopup = show)
    }

    private fun openCamera(openCamera: Boolean) {
        _state.value = _state.value.copy(openCamera = openCamera)
    }

    private fun openGallery(openGallery: Boolean) {
        _state.value = _state.value.copy(openGallery = openGallery)
    }
}