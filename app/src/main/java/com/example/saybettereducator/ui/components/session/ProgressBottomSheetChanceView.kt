package com.example.saybettereducator.ui.components.session

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.saybettereducator.R
import com.example.saybettereducator.ui.model.CommunicationType
import com.example.saybettereducator.ui.model.ProgressState
import com.example.saybettereducator.ui.theme.Gray5B
import com.example.saybettereducator.ui.theme.MainGreen_85
import com.example.saybettereducator.ui.theme.Red

@Composable
fun ProgressBottomSheetChanceView(
    state: ProgressState,
    onChanceClick: () -> Unit,
    onTimerClick: () -> Unit
) {
    Spacer(modifier = Modifier.size(8.dp))

    ProgressBottomSheetTitle(title = R.string.progress_chance_title)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        ProgressBasicBtn(
            resId = R.drawable.ic_progress_chance_btn,
            desc = R.string.progress_chance,
            onClick = onChanceClick,
            backgroundColor = when (state.communicationState) {
                CommunicationType.NotCommunicating -> Gray5B
                else -> MainGreen_85
            },
            textColor = Color.White
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "${state.communicationCount}/10",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.weight(1f))

        ProgressBasicBtn(
            resId = R.drawable.ic_progress_timer,
            desc = R.string.progress_timer,
            onClick = onTimerClick,
            backgroundColor = when (state.communicationState) {
                CommunicationType.Paused -> Red
                else -> Gray5B
            },
            textColor = Color.White
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = "${state.timerTime / 1000}",
            color = when (state.communicationState) {
                CommunicationType.Paused -> Red
                else -> Color.White
            },
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

