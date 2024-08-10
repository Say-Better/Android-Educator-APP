package com.example.saybettereducator.ui.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.saybettereducator.R
import com.example.saybettereducator.ui.components.progress.ProgressBottomBar
import com.example.saybettereducator.ui.components.progress.ProgressBottomSheet
import com.example.saybettereducator.ui.components.progress.ProgressTopBar
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
    val isCameraOn = true

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
                VideoSection(isCameraOn)

                Spacer(modifier = Modifier.weight(1f))
            }

            BottomSheetScaffold(
                scaffoldState = bottomSheetScaffoldState,
                sheetContent = {
                    ProgressBottomSheet()
                },
                sheetDragHandle = {
                    DragHandle(bottomSheetScaffoldState, scope)
                },
                sheetContainerColor = colorResource(id = R.color.dark_gray),
                sheetPeekHeight = 30.dp, // 초기 바텀시트가 보여지는 높이
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ) {}
        }
    }
}


@Composable
fun VideoSection(isCameraOn: Boolean) {
    if (isCameraOn) {
        Image(
            painter = painterResource(id = R.drawable.educator_cam),
            contentDescription = "교육자 캠",
            modifier = Modifier
                .size(width = 328.dp, height = 196.dp)
                .clip(RoundedCornerShape(16.dp))
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.learner_cam),
            contentDescription = "학습자 캠",
            modifier = Modifier
                .padding(top = 12.dp)
                .size(width = 328.dp, height = 196.dp)
                .clip(RoundedCornerShape(16.dp))
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
                .background(colorResource(id = R.color.bottom_bar), RoundedCornerShape(4.dp))
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(widthDp = 360, heightDp = 800)
@Composable
fun SolutionProgressScreenPreview() {
    SolutionProgressScreen()
}
