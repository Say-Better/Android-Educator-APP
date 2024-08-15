package com.example.saybettereducator.ui.components.session

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.example.saybettereducator.data.model.Symbol

@Composable
fun ProgressLearningView(
    selectedMode: Int,
    selectedSymbols: List<Symbol>,
    allSymbols: List<Symbol>,
    playingSymbol: Symbol?,
    onSymbolClick: (Symbol?) -> Unit
) {
    Log.d("ProgressLearningView", "Recomposing with selectedSymbols: $selectedSymbols")
    when (selectedMode) {
        1 -> {
            val symbol = selectedSymbols.getOrNull(0)
            SymbolCard(
                mode = selectedMode,
                symbol = symbol,
                isPlaying = symbol != null && symbol == playingSymbol,
                onSymbolClick = { onSymbolClick(symbol) },
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
                for (i in 0 until 2) {
                    val symbol = selectedSymbols.getOrNull(i)
                    SymbolCard(
                        mode = selectedMode,
                        symbol = symbol,
                        isPlaying = symbol != null && symbol == playingSymbol,
                        onSymbolClick = { onSymbolClick(symbol) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(216.dp)
                    )
                    if (i < 1) Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        3 -> {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                for (i in 0 until 2) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        for (j in 0 until 2) {
                            val index = i * 2 + j
                            val symbol = selectedSymbols.getOrNull(index)
                            SymbolCard(
                                mode = selectedMode,
                                symbol = symbol,
                                isPlaying = symbol != null && symbol == playingSymbol,
                                onSymbolClick = { onSymbolClick(symbol) },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(205.dp)
                            )
                            if (j < 1) Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                    if (i < 1) Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        4 -> {
            val pageCount = (allSymbols.size + 8) / 9 // 3x3 페이지 수 계산
            val pagerState = rememberPagerState(pageCount = { pageCount })

            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 16.dp), // 좌우 패딩 추가
                pageSpacing = 8.dp,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
            ) { pageIndex ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val symbolsForPage = allSymbols.drop(pageIndex * 9).take(9)

                    for (rowIndex in 0 until 3) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            for (colIndex in 0 until 3) {
                                val symbolIndex = rowIndex * 3 + colIndex
                                val symbol = symbolsForPage.getOrNull(symbolIndex)

                                if (symbol != null) {
                                    SymbolCard(
                                        mode = selectedMode,
                                        symbol = symbol,
                                        isPlaying = symbol == playingSymbol,
                                        onSymbolClick = { onSymbolClick(symbol) },
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(131.dp)
                                    )
                                } else {
                                    Spacer(
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(131.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ProgressLearningPreview() {
    ProgressLearningView(
        selectedMode = 1,
        selectedSymbols = emptyList(),
        allSymbols = emptyList(),
        playingSymbol = null,
        onSymbolClick = {}
    )
}
