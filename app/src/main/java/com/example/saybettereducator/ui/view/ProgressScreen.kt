package com.example.saybettereducator.ui.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.saybettereducator.R
import com.example.saybettereducator.ui.components.progress.ProgressBottomBar
import com.example.saybettereducator.ui.components.progress.ProgressBottomSheet
import com.example.saybettereducator.ui.components.progress.ProgressLearningView
import com.example.saybettereducator.ui.components.progress.ProgressTopBar
import com.example.saybettereducator.ui.theme.BottomBar
import com.example.saybettereducator.ui.theme.DarkGray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolutionProgressScreen() {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(initialValue = SheetValue.PartiallyExpanded)
    )
    val scope = rememberCoroutineScope()
    var selectedMode by remember { mutableIntStateOf(1) }

    Scaffold(
        topBar = {
            ProgressTopBar()
        },
        bottomBar = {
            ProgressBottomBar()
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Black)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                ProgressLearningView(selectedMode)
                Spacer(modifier = Modifier.weight(1f))
                VideoSection()
                Spacer(modifier = Modifier.weight(1f))
            }

            BottomSheetScaffold(
                scaffoldState = bottomSheetScaffoldState,
                sheetContent = {
                    ProgressBottomSheet(
                        onModeSelected = { mode ->
                            selectedMode = mode
                        }
                    )
                },
                sheetDragHandle = {
                    DragHandle(bottomSheetScaffoldState, scope)
                },
                sheetContainerColor = DarkGray,
                sheetPeekHeight = 30.dp, // 초기 바텀시트가 보여지는 높이
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ) {}
        }
    }
}


@Composable
fun VideoSection() {
    Row {
        Image(
            painter = painterResource(id = R.drawable.educator_cam),
            contentDescription = "교육자 캠",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .size(width = 128.dp, height = 75.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.learner_cam),
            contentDescription = "학습자 캠",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(start = 8.dp)
                .clip(RoundedCornerShape(8.dp))
                .size(width = 128.dp, height = 75.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DragHandle(bottomSheetScaffoldState: BottomSheetScaffoldState, scope: CoroutineScope) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(24.dp)
            .padding(vertical = 8.dp)
            .clickable {
                scope.launch {
                    if (bottomSheetScaffoldState.bottomSheetState.currentValue == SheetValue.PartiallyExpanded) {
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    } else {
                        bottomSheetScaffoldState.bottomSheetState.partialExpand()
                    }
                }
            }
    ) {
        Box(
            modifier = Modifier
                .width(130.dp)
                .height(4.dp)
                .background(BottomBar, RoundedCornerShape(4.dp))
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(widthDp = 360, heightDp = 800)
@Composable
fun SolutionProgressScreenPreview() {
    SolutionProgressScreen()
}
