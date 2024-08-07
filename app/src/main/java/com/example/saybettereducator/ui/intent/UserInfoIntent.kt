package com.example.saybettereducator.ui.intent

import android.net.Uri

sealed class UserInfoIntent {
    object Refresh : UserInfoIntent()
    data class UpdateName(val name: String) : UserInfoIntent()
    data class UpdateProfileImage(val uri: Uri) : UserInfoIntent()
    data class ShowPopup(val showPopup: Boolean) : UserInfoIntent()
    data class OpenCamera(val openCamera: Boolean) : UserInfoIntent()
    data class OpenGallery(val openGallery: Boolean) : UserInfoIntent()
}