package com.example.saybettereducator.ui.components.progress

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.saybettereducator.domain.model.Symbol

@Composable
fun ProgressBottomSheet(
    symbols: List<Symbol>,
    selectedSymbols: List<Symbol>,
    onModeSelected: (Int) -> Unit,
    onItemClick: (Symbol) -> Unit,
    onAddClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        ProgressChanceView(onChanceClick = {}, onTimerClick = {})
        ProgressModeView(onModeSelected = onModeSelected)

        ProgressBottomSheetSymbol(
            symbols = symbols,
            selectedSymbols = selectedSymbols,
            onItemClick = onItemClick,
            onAddClick = onAddClick
        )
    }
}


@Preview
@Composable
fun ProgressBottomSheetPreview() {
    ProgressBottomSheet(emptyList(), emptyList(), {}, {}, {})
}
