package com.example.saybettereducator.ui.components.progress

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
import androidx.compose.ui.unit.times
import com.example.saybettereducator.R
import com.example.saybettereducator.ui.theme.Gray100
import com.example.saybettereducator.ui.theme.Gray50
import com.example.saybettereducator.ui.theme.Gray5B
import com.example.saybettereducator.ui.theme.Gray5B50

@Composable
fun ProgressBottomSheetSymbol(
    items: List<String>,
    onItemClick: (String) -> Unit,
    onAddClick: () -> Unit
) {
    Spacer(modifier = Modifier.size(20.dp))

    ProgressBottomSheetTitle(R.string.progress_symbol_title)

    SymbolCard(items, onItemClick, onAddClick)
}


@Composable
fun SymbolCard(
    items: List<String>,
    onItemClick: (String) -> Unit,
    onAddClick: () -> Unit
) {
    val itemPadding = 4.dp
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier
            .height(2 * 100.dp)
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

        items(items.size) { index ->
            val item = items[index]
            Box(
                modifier = Modifier
                    .padding(itemPadding)
                    .aspectRatio(1f)
                    .fillMaxWidth(0.25f) // 전체 너비의 1/4
                    .background(Gray5B50, RoundedCornerShape(8.dp))
                    .clickable { onItemClick(item) },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.learner_cam),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Gray5B50, RoundedCornerShape(8.dp)), // 반투명 배경
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item,
                        color = Color.White,
                        fontSize = 12.sp,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ProgressSymbolViewPreview() {
    val items = List(20) { "Item ${it + 1}" } // 예시로 19개의 아이템 생성
    ProgressBottomSheetSymbol(
        items = items,
        onItemClick = { clickedItem ->
            println("Clicked on: $clickedItem")
        },
        onAddClick = {
            println("Add item clicked")
        }
    )
}
