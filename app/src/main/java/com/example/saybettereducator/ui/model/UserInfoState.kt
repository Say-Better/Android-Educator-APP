package com.example.saybettereducator.ui.model

import android.net.Uri

data class UserInfoState(
    val profileImageUrl: Uri? = null,
    val name: String = "교육자",
    val showPopup: Boolean = false,
    val openCamera: Boolean = false,
    val openGallery: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)