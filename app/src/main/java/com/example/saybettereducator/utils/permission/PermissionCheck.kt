package com.example.saybettereducator.utils.permission

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.core.content.ContextCompat

fun checkAndRequestPermissions(
    context: Context,
    permissions: Array<String>,
    launcher: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>,
    onPermissionsGranted: () -> Unit
) {
    /** 권한이 이미 있는 경우 **/
    if (permissions.all {
            ContextCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }) {
        Log.d("test5", "권한이 이미 존재합니다.")
        onPermissionsGranted()
    }

    /** 권한이 없는 경우 **/
    else {
        launcher.launch(permissions)
        Log.d("test5", "권한을 요청하였습니다.")
    }
}