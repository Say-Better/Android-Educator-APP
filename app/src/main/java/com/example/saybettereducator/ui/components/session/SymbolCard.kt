package com.example.saybettereducator.ui.components.session

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.saybettereducator.R
import com.example.saybettereducator.data.model.Symbol
import com.example.saybettereducator.ui.theme.DarkGray
import com.example.saybettereducator.ui.theme.Gray5B
import com.example.saybettereducator.ui.theme.HighlightBorder
import com.example.saybettereducator.ui.theme.LightGray
import com.example.saybettereducator.ui.theme.pretendardBoldFont
import com.example.saybettereducator.ui.theme.pretendardRegularFont

@Composable
fun SymbolCard(
    mode: Int,
    symbol: Symbol?,
    isPlaying: Boolean,
    onSymbolClick: (Symbol?) -> Unit,
    modifier: Modifier = Modifier
) {
    val outRound = when(mode) { 1,2,3 -> 16.dp else -> 8.dp }
    val inRound = when(mode) { 1,2,3 -> 12.dp else -> 4.dp }
    val innerPadding = when(mode) { 1 -> 12.dp 2,3 -> 8.dp else -> 5.dp }
    val borderColor = if (isPlaying) HighlightBorder else Gray5B
    val textColor = if (isPlaying) HighlightBorder else Color.White
    Log.d("SymbolCard", "Recomposing with symbol: $symbol")

    val myFont = remember { FontFamily(pretendardRegularFont) }

    
    Box(
        modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(size = outRound)
            )
            .background(
                color = DarkGray,
                shape = RoundedCornerShape(size = outRound)
            ).clickable { onSymbolClick(symbol) },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (symbol != null) {
                Image(
                    painter = painterResource(id = symbol.imageRes),
                    contentDescription = "symbol",
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxWidth()
                        .weight(1f)
                        .background(
                            color = LightGray,
                            shape = RoundedCornerShape(size = inRound)
                        ),
                    )
            } else {
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxWidth()
                        .weight(1f)
                        .background(
                            color = LightGray,
                            shape = RoundedCornerShape(size = inRound)
                        ),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 기본 빈 상태 표시
                    Image(
                        painter = painterResource(id = R.drawable.progress_add_symbol),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                    if (mode == 1) {
                        Text(
                            text = stringResource(id = R.string.progress_learning_select_symbol),
                            modifier = Modifier.padding(top = 16.dp),
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 22.4.sp,
                                fontFamily = myFont,
                                fontWeight = FontWeight(500),
                                color = Gray5B,
                                textAlign = TextAlign.Center
                            )
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(0.05f))
            Text(
                text = symbol?.name ?: "-",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily(pretendardBoldFont),
                    fontWeight = FontWeight(500),
                    color = textColor,
                    textAlign = TextAlign.Center
                )
            )
            Spacer(modifier = Modifier.weight(0.07f))
        }
    }
}

@Preview
@Composable
fun SymbolCardPreview() {
    SymbolCard(1, null, false, {})
}
