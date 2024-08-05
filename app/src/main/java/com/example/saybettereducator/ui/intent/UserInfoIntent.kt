package com.example.saybettereducator.ui.intent

import android.net.Uri

sealed class UserInfoIntent {
    object Refresh : UserInfoIntent()
    data class UpdateName(val name: String) : UserInfoIntent()
    data class UpdateProfileImage(val uri: Uri) : UserInfoIntent()
    data class UpdateShowPopup(val showPopup: Boolean) : UserInfoIntent()
    data class UpdateOpenCamera(val openCamera: Boolean) : UserInfoIntent()
    data class UpdateOpenGallery(val openGallery: Boolean) : UserInfoIntent()
}