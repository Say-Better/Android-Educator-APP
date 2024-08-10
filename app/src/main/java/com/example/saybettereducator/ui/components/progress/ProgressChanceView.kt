package com.example.saybettereducator.ui.components.progress

import androidx.compose.foundation.layout.Arrangement
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

@Composable
fun ProgressChanceView(
    onChanceClick: () -> Unit,
    onTimerClick: () -> Unit
) {
    Spacer(modifier = Modifier.size(8.dp))

    ProgressBottomSheetTitle(title = R.string.solution_progress_chance_title)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f) // Assign weight to make it take available space
        ) {
            ProgressBasicBtn(
                resId = R.drawable.progress_chance_btn,
                desc = R.string.solution_progress_chance,
                onClick = onChanceClick
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "0/10",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.weight(1f) // Assign weight to align to the end
        ) {
            ProgressBasicBtn(
                resId = R.drawable.progress_timer,
                desc = R.string.solution_progress_timer,
                onClick = onTimerClick
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "00:00",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}