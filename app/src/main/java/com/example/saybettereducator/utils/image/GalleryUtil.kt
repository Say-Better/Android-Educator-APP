package com.example.saybettereducator.utils.image

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

class GalleryUtil(private val context: Context) {
    fun openGallery(launcher: ActivityResultLauncher<String>) {
        launcher.launch("image/*")
    }
}