package com.example.saybettereducator.ui.components.progress

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.saybettereducator.data.model.Symbol
import com.example.saybettereducator.ui.model.ProgressState

@Composable
fun ProgressBottomSheet(
    state: ProgressState,
    onChanceClick: () -> Unit,
    onTimerClick: () -> Unit,
    onModeSelected: (Int) -> Unit,
    onItemClick: (Symbol) -> Unit,
    onAddClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        // 업데이트된 컴포저블 호출
        ProgressBottomSheetChanceView(
            state = state,
            onChanceClick = onChanceClick,
            onTimerClick = onTimerClick
        )
        ProgressModeView(onModeSelected = onModeSelected)

        ProgressBottomSheetSymbol(
            symbols = state.symbols,
            selectedSymbols = state.selectedSymbols,
            onItemClick = onItemClick,
            onAddClick = onAddClick
        )
    }
}


@Preview
@Composable
fun ProgressBottomSheetPreview() {
    ProgressBottomSheet(
        state = ProgressState(),
        onModeSelected = {},
        onItemClick = {},
        onAddClick = {},
        onChanceClick = {},
        onTimerClick = {}
    )
}