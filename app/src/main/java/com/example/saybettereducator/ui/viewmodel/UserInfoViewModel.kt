package com.example.saybettereducator.ui.viewmodel

import android.net.Uri
import com.example.saybettereducator.ui.common.MviViewModel
import com.example.saybettereducator.ui.intent.UserInfoIntent
import com.example.saybettereducator.ui.model.UserInfoState
import com.example.saybettereducator.ui.sideeffect.UserInfoSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
) : MviViewModel<UserInfoState, UserInfoSideEffect, UserInfoIntent>(UserInfoState()) {

    override fun handleIntent(intent: UserInfoIntent) {
        when (intent) {
            is UserInfoIntent.Refresh -> loadProfile()
            is UserInfoIntent.UpdateProfileImage -> updateProfileImage(intent.uri)
            is UserInfoIntent.UpdateName -> updateName(intent.name)
            is UserInfoIntent.ShowPopup -> showPopup(intent.showPopup)
            is UserInfoIntent.NavigateHome -> navigateToHome()
            is UserInfoIntent.NavigateLoginSuccess -> navigateToLoginSuccess()
            is UserInfoIntent.OnGalleryPictureTaken -> onGalleryPictureTaken()
            is UserInfoIntent.OnCameraPictureTaken -> onCameraPictureTaken()
        }
    }

    private fun onGalleryPictureTaken() {
    }

    private fun onCameraPictureTaken() {
    }

    private fun navigateToHome() {
        postSideEffect(UserInfoSideEffect.NavigateHome)
    }

    private fun navigateToLoginSuccess() {
        postSideEffect(UserInfoSideEffect.NavigateLoginSuccess)
    }

    private fun loadProfile() {
        // Handle loading profile logic
    }

    private fun updateProfileImage(uri: Uri?) {
        updateState { it.copy(profileImageUrl = uri) }
    }

    private fun updateName(name: String) {
        updateState { it.copy(name = name) }
    }

    private fun showPopup(show: Boolean) {
        updateState { it.copy(showPopup = show) }
    }
}