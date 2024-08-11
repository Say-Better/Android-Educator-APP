package com.example.saybettereducator.ui.components.progress

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProgressBottomSheet(onModeSelected: (Int) -> Unit) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp)) {
        ProgressChanceView(onChanceClick = {}, onTimerClick = {})
        ProgressModeView(onModeSelected = onModeSelected)

        val items = List(20) { "Item ${it + 1}" } // 예시로 20개의 아이템 생성

        ProgressBottomSheetSymbol(items, onItemClick = {}, onAddClick = {})
    }
}


@Preview
@Composable
fun ProgressBottomSheetPreview() {
    ProgressBottomSheet({})
}
