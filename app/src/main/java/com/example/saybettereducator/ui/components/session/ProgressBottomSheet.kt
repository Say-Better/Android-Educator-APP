package com.example.saybettereducator.ui.components.session

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.UiMode
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.saybettereducator.data.model.Symbol
import com.example.saybettereducator.ui.model.ProgressState
import com.example.saybettereducator.ui.theme.PretendardTypography
import com.example.saybettereducator.ui.theme.White
import com.example.saybettereducator.ui.theme.pretendardMediumFont

@Composable
fun ProgressBottomSheet(
    state: ProgressState,
    onChanceClick: () -> Unit,
    onTimerClick: () -> Unit,
    onModeSelected: (Int) -> Unit,
    onItemClick: (Symbol) -> Unit,
    onAddClick: () -> Unit,
    onTextChange: (String) -> Unit,
    onAddTextSymbol: (String) -> Unit
) {
    if (!state.isTextSymbolModeActivating) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
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
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 8.dp)
        ) {
            Text(
                text = "텍스트 입력하여 상징 생성하기",

                // F) Button2_Sb
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 20.8.sp,
                    fontFamily = FontFamily(pretendardMediumFont),
                    fontWeight = FontWeight(600),
                    color = Color(0xFFFFFFFF),
                ),
                modifier = Modifier
                    .padding(bottom = 12.dp)
            )

            // text field
            BasicTextField(
                value = state.inputState,
                onValueChange = { onTextChange(it) },
                textStyle = PretendardTypography.bodyMedium.copy(Color.Black),
                singleLine = true,
                maxLines = 1,
                modifier = Modifier
                    .padding(bottom = 100.dp)
                    .fillMaxWidth()
                    .height(44.dp)
                    .background(color = White, shape = RoundedCornerShape(size = 12.dp))
                    .border(
                        width = 1.dp,
                        color = Color(0xFF5B5B5B),
                        shape = RoundedCornerShape(size = 12.dp)
                    )
            )

            Box(
                modifier = Modifier
                    .width(328.dp)
                    .height(56.dp)
                    .background(
                        color = if(state.inputState == "") Color(0xE5FFFFFF) else Color(0xD95FD399),
                        shape = RoundedCornerShape(size = 32.dp)
                    )
                    .clickable {
                        onAddTextSymbol(state.inputState)
                        onTextChange("")
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "입력 완료",

                    // Button1_Sb
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 25.2.sp,
                        fontFamily = FontFamily(pretendardMediumFont),
                        fontWeight = FontWeight(600),
                        color = if(state.inputState == "") Color(0xFF5B5B5B) else White,
                        textAlign = TextAlign.Center,
                    )
                )
            }
        }
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
        onTimerClick = {},
        onTextChange = {},
        onAddTextSymbol = {}
    )
}