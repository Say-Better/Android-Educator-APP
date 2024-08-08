package com.example.saybettereducator.utils.image

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.core.content.FileProvider
import java.io.File

class CameraUtil(private val context: Context) {
    private var imageUri: Uri? = null

    fun openCamera(launcher: ActivityResultLauncher<Uri>): Uri? {
        val imageFile = File(context.cacheDir, "camera_image.jpg")
        imageUri = FileProvider.getUriForFile(context, "${context.packageName}.provider", imageFile)
        launcher.launch(imageUri!!)
        return imageUri
    }
}
