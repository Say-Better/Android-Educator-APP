package com.example.saybettereducator.ui.components.session

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.saybettereducator.R

@Composable
fun ProgressModeView(
    onModeSelected: (Int) -> Unit
) {
    var selectedMode by remember { mutableIntStateOf(1) }

    Spacer(modifier = Modifier.size(20.dp))

    ProgressBottomSheetTitle(R.string.progress_mode_title)

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Start
        ) {
            ModeButton(
                isSelected = selectedMode == 1,
                activeImageRes = R.drawable.progress_mode_1_active,
                inactiveImageRes = R.drawable.progress_mode_1,
                onClick = {
                    selectedMode = 1
                    onModeSelected(1)
                }
            )

            Spacer(modifier = Modifier.size(8.dp))

            ModeButton(
                isSelected = selectedMode == 2,
                activeImageRes = R.drawable.progress_mode_2_active,
                inactiveImageRes = R.drawable.progress_mode_2,
                onClick = {
                    selectedMode = 2
                    onModeSelected(2)
                }
            )

            Spacer(modifier = Modifier.size(8.dp))

            ModeButton(
                isSelected = selectedMode == 3,
                activeImageRes = R.drawable.progress_mode_3_active,
                inactiveImageRes = R.drawable.progress_mode_3,
                onClick = {
                    selectedMode = 3
                    onModeSelected(3)
                }
            )
        }

        // 네 번째 버튼은 오른쪽에 배치
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.End
        ) {
            ModeButton(
                isSelected = selectedMode == 4,
                activeImageRes = R.drawable.progress_mode_4_active,
                inactiveImageRes = R.drawable.progress_mode_4,
                onClick = {
                    selectedMode = 4
                    onModeSelected(4)
                }
            )
        }
    }
}

@Composable
fun ModeButton(
    isSelected: Boolean,
    activeImageRes: Int,
    inactiveImageRes: Int,
    onClick: () -> Unit
) {
    Image(
        painter = painterResource(id = if (isSelected) activeImageRes else inactiveImageRes),
        contentDescription = null,
        modifier = Modifier
            .size(32.dp)
            .clickable(onClick = onClick)
    )
}