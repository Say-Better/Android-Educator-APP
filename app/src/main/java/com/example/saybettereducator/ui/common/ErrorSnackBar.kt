package com.example.saybettereducator.ui.common

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun CoroutineScope.showErrorSnackBar(
    snackbarHostState: SnackbarHostState,
    message: String = "Something went wrong",
    actionLabelType: ActionLabelType = ActionLabelType.RETRY,
    onClink: () -> Unit
) {
    launch { 
        snackbarHostState.currentSnackbarData?.dismiss()
        
        val result = snackbarHostState.showSnackbar(
            message = message,
            actionLabel = actionLabelType.name,
            duration = SnackbarDuration.Short
        )
        
        when (result) {
            SnackbarResult.Dismissed -> { }
            SnackbarResult.ActionPerformed -> onClink()
        }
    }
}

enum class ActionLabelType {
    RETRY,
    DISMISS
}