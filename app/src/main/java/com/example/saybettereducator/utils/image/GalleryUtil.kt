package com.example.saybettereducator.utils.image

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

class GalleryUtil {

    private lateinit var selectedImageUri: Uri

    @Composable
    fun openGallery(onSelectImage: (Uri) -> Unit) {
        selectImage(onSelectImage).launch("image/*")
    }

    @Composable
    fun selectImage(onSelectImage: (Uri) -> Unit) =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let { onSelectImage(uri) }
        }
}