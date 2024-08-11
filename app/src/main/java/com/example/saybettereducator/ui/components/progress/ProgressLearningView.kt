package com.example.saybettereducator.ui.components.progress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProgressLearningView(selectedMode: Int) {
    when (selectedMode) {
        1 -> {
            SymbolCard(
                selectedMode,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .height(376.dp)
            )
        }

        2 -> {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                SymbolCard(
                    selectedMode,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(216.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                SymbolCard(
                    selectedMode,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(216.dp)
                )
            }
        }

        3 -> {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    SymbolCard(
                        selectedMode,
                        modifier = Modifier
                            .weight(1f)
                            .height(205.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    SymbolCard(
                        selectedMode,
                        modifier = Modifier
                            .weight(1f)
                            .height(205.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    SymbolCard(
                        selectedMode,
                        modifier = Modifier
                            .weight(1f)
                            .height(205.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    SymbolCard(
                        selectedMode,
                        modifier = Modifier
                            .weight(1f)
                            .height(205.dp)
                    )
                }
            }
        }

        4 -> {
            val pagerState = rememberPagerState(pageCount = { 3 })
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) { page ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    for (i in 0..2) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            for (j in 0..2) {
                                SymbolCard(
                                    selectedMode,
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(131.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}




@Preview
@Composable
fun ProgressLearningPreview() {
    ProgressLearningView(1)
}