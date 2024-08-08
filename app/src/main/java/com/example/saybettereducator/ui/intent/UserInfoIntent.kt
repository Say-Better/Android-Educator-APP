package com.example.saybettereducator.ui.intent

import android.net.Uri

sealed class UserInfoIntent {
    object Refresh : UserInfoIntent()
    object NavigateHome : UserInfoIntent()
    object NavigateLoginSuccess : UserInfoIntent()
    data class UpdateName(val name: String) : UserInfoIntent()
    data class UpdateProfileImage(val uri: Uri?) : UserInfoIntent()
    data class ShowPopup(val showPopup: Boolean) : UserInfoIntent()

    data class OnGalleryPictureTaken(val uri: Uri) : UserInfoIntent()
    object OnCameraPictureTaken : UserInfoIntent()
}