package com.example.saybettereducator.ui.components.progress

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProgressLearningView(selectedMode: Int) {
    when (selectedMode) {
        1 -> {
            ProgressLearningMode1()
        }

        2 -> {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                ProgressLearningMode2()
                Spacer(modifier = Modifier.height(8.dp))
                ProgressLearningMode2()
            }
        }

        3 -> {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                for (i in 0..1) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        for (j in 0..1) {
                            ProgressLearningMode3(Modifier.weight(1f))
                            if (j < 1) Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                    if (i < 1) Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        4 -> ProgressLearningMode4()
    }
}



@Preview
@Composable
fun ProgressLearningPreview() {
    ProgressLearningView(4)
}