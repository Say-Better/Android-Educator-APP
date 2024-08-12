package com.example.saybettereducator.ui.view

import android.os.Build
import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.saybettereducator.R
import com.example.saybettereducator.ui.components.progress.ProgressBottomBar
import com.example.saybettereducator.ui.components.progress.ProgressBottomSheet
import com.example.saybettereducator.ui.components.progress.ProgressLearningView
import com.example.saybettereducator.ui.components.progress.ProgressTopBar
import com.example.saybettereducator.ui.intent.ProgressIntent
import com.example.saybettereducator.ui.model.ProgressState
import com.example.saybettereducator.ui.sideeffect.ProgressSideEffect
import com.example.saybettereducator.ui.theme.BottomBar
import com.example.saybettereducator.ui.theme.DarkGray
import com.example.saybettereducator.ui.viewmodel.ProgressViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(viewModel: ProgressViewModel = hiltViewModel()) {
    val viewState by viewModel.collectAsState()
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(initialValue = SheetValue.PartiallyExpanded)
    )
    val scope = rememberCoroutineScope()

// SideEffect 처리
    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is ProgressSideEffect.OpenBottomSheet -> {
                scope.launch {
                    scaffoldState.bottomSheetState.expand()
                }
            }
            is ProgressSideEffect.CloseBottomSheet -> {
                scope.launch {
                    scaffoldState.bottomSheetState.partialExpand()
                }
            }
        }
    }

    Log.d("ProgressScreen", "Recomposing with state: $viewState")

    ProgressScreen(
        state = viewState,
        scaffoldState = scaffoldState,
        scope = scope,
        onIntent = viewModel::handleIntent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressScreen(
    state: ProgressState,
    scaffoldState: BottomSheetScaffoldState,
    scope: CoroutineScope,
    onIntent: (ProgressIntent) -> Unit
) {
    Log.d("ProgressScreen", "Recomposing with state: $state")

    Scaffold(
        topBar = { ProgressTopBar(isPlaying = state.isVoicePlaying) },
        bottomBar = { ProgressBottomBar() }
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
                ProgressLearningView(
                    selectedMode = state.selectedMode,
                    selectedSymbols = state.selectedSymbols,
                    allSymbols = state.symbols,
                    playingSymbol = state.playingSymbol,
                    onSymbolClick = { symbol ->
                        onIntent(ProgressIntent.SymbolClicked(symbol))
                    }
                )
                Spacer(modifier = Modifier.weight(1f))
                VideoSection()
                Spacer(modifier = Modifier.weight(1f))
            }

            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                sheetContent = {
                    ProgressBottomSheet(
                        symbols = state.symbols,
                        selectedSymbols = state.selectedSymbols,
                        onModeSelected = { mode ->
                            onIntent(ProgressIntent.SelectMode(mode))
                        },
                        onItemClick = { symbol ->
                            if (state.selectedSymbols.contains(symbol)) {
                                onIntent(ProgressIntent.DeselectSymbol(symbol))
                            } else {
                                onIntent(ProgressIntent.SelectSymbol(symbol))
                            }
                        },
                        onAddClick = {}
                    )
                },
                sheetDragHandle = {
                    DragHandle(onClick = { onIntent(ProgressIntent.ToggleBottomSheet) })
                },
                sheetContainerColor = DarkGray,
                sheetPeekHeight = 30.dp,
                modifier = Modifier.align(Alignment.BottomCenter)
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

@Composable
fun DragHandle(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(24.dp)
            .padding(vertical = 8.dp)
            .clickable { onClick() }
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
fun ProgressScreenPreview() {
    ProgressScreen()
}
