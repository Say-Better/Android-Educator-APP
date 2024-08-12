package com.example.saybettereducator.ui.components.progress

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import com.example.saybettereducator.R
import com.example.saybettereducator.data.model.Symbol
import com.example.saybettereducator.ui.theme.Gray5B
import com.example.saybettereducator.ui.theme.Gray5B50
import com.example.saybettereducator.ui.theme.Transparent

@Composable
fun ProgressBottomSheetSymbol(
    symbols: List<Symbol>,
    selectedSymbols: List<Symbol>,
    onItemClick: (Symbol) -> Unit,
    onAddClick: () -> Unit
) {
    Spacer(modifier = Modifier.size(20.dp))

    ProgressBottomSheetTitle(R.string.progress_symbol_title)

    BottomSheetSymbolCard(symbols, selectedSymbols, onItemClick, onAddClick)
}


@Composable
fun BottomSheetSymbolCard(
    symbols: List<Symbol>,
    selectedSymbols: List<Symbol>,
    onItemClick: (Symbol) -> Unit,
    onAddClick: () -> Unit
) {
    val itemPadding = 4.dp
    val columns = 4 // 4열 고정
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp // 화면 너비
    val itemHeight = (screenWidth - (itemPadding * (columns - 1))) / columns // 각 아이템의 너비 = 높이

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier
            .fillMaxWidth()
            .height(itemHeight * 2 + itemPadding),
    ) {
        item {
            // 첫 번째 아이템은 '아이템 추가' 버튼
            Column(
                modifier = Modifier
                    .padding(itemPadding)
                    .aspectRatio(1f)
                    .fillMaxWidth(0.25f) // 전체 너비의 1/4
                    .background(Color.White, RoundedCornerShape(8.dp))
                    .clickable { onAddClick() },
                horizontalAlignment = Alignment.CenterHorizontally, // 수평 중앙 정렬
                verticalArrangement = Arrangement.Center // 수직 중앙 정렬
            ) {
                Image(
                    painter = painterResource(id = R.drawable.progress_add_symbol),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(19.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.height(8.dp)) // 이미지와 텍스트 사이 간격 조절
                Text(
                    text = "텍스트 상징",
                    color = Gray5B,
                    fontSize = 12.sp
                )
            }
        }

        items(symbols.size) { index ->
            val item = symbols[index]
            val isSelected = selectedSymbols.contains(item)
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .aspectRatio(1f)
                    .fillMaxWidth(0.25f)
                    .background(
                        Color.White,
                        RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        onItemClick(item)
                        Log.d("BottomSheetSymbolCard", "Item clicked: $item")
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = if (isSelected) Transparent else Gray5B50,
                            shape = RoundedCornerShape(8.dp)
                        )
                )
                Text(
                    text = item.name,
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(4.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun ProgressSymbolViewPreview() {
    ProgressBottomSheetSymbol(
        symbols = emptyList(),
        selectedSymbols = emptyList(),
        onItemClick = { clickedItem ->
            println("Clicked on: $clickedItem")
        },
        onAddClick = {
            println("Add item clicked")
        }
    )
}
