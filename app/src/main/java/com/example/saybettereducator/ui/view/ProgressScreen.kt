package com.example.saybettereducator.ui.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.saybettereducator.data.repository.MainServiceRepository
import com.example.saybettereducator.ui.components.session.ProgressBottomSheet
import com.example.saybettereducator.ui.components.session.ProgressLearningView
import com.example.saybettereducator.ui.components.session.ProgressTopBar
import com.example.saybettereducator.ui.components.session.ReadyHelloBtn
import com.example.saybettereducator.ui.components.session.ReadyTopBar
import com.example.saybettereducator.ui.components.session.SessionBottomBar
import com.example.saybettereducator.ui.components.session.SessionVideoView
import com.example.saybettereducator.ui.intent.ProgressIntent
import com.example.saybettereducator.ui.intent.SessionIntent
import com.example.saybettereducator.ui.model.ProgressState
import com.example.saybettereducator.ui.model.SessionState
import com.example.saybettereducator.ui.theme.BottomBar
import com.example.saybettereducator.ui.theme.DarkGray

/*
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionScreen(
    sessionState: SessionState,
    progressState: ProgressState,
    scaffoldState: BottomSheetScaffoldState,
    onSessionIntent: (SessionIntent) -> Unit,
    onProgressIntent: (ProgressIntent) -> Unit,
    repository: MainServiceRepository
)  {
    Scaffold(
        topBar = {
            if (!sessionState.isStart) ReadyTopBar(repository)
            else ProgressTopBar(isPlaying = progressState.isVoicePlaying)
        },
        bottomBar = {
            SessionBottomBar(
                sessionState.isStart,
                repository,
                onStartSolution = { onSessionIntent(SessionIntent.StartSession) }
            )
        }
    ) { innerPadding ->
        Surface(color = Color.Black) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.weight(1f))
                // 세션 진행 상징 카드 화면
                if(sessionState.isStart) {
                    ProgressLearningView(
                        selectedMode = progressState.selectedMode,
                        selectedSymbols = progressState.selectedSymbols,
                        allSymbols = progressState.symbols,
                        playingSymbol = progressState.playingSymbol,
                        onSymbolClick = { symbol ->
                            onProgressIntent(ProgressIntent.SymbolClicked(symbol))
                        }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }

                SessionVideoView(sessionState = sessionState)
                Spacer(modifier = Modifier.weight(1f))

                if(!sessionState.isStart) {
                    ReadyHelloBtn(onHelloClick = { onSessionIntent(SessionIntent.SendRTCMessage("Hello!!")) })
                    Spacer(modifier = Modifier.weight(1f))
                } else {
                    BottomSheetScaffold(
                        scaffoldState = scaffoldState,
                        sheetContent = {
                            ProgressBottomSheet(
                                state = progressState,
                                onChanceClick = {
                                    onProgressIntent(ProgressIntent.CommunicationClicked)
                                },
                                onTimerClick = {
                                    onProgressIntent(ProgressIntent.TimerClicked)
                                },
                                onModeSelected = { mode ->
                                    onProgressIntent(ProgressIntent.SelectMode(mode))
                                },
                                onItemClick = { symbol ->
                                    if (progressState.selectedSymbols.contains(symbol)) {
                                        onProgressIntent(ProgressIntent.DeselectSymbol(symbol))
                                    } else {
                                        onProgressIntent(ProgressIntent.SelectSymbol(symbol))
                                    }
                                },
                                onAddClick = {}
                            )
                        },
                        sheetDragHandle = {
                            DragHandle(onClick = { onProgressIntent(ProgressIntent.ToggleBottomSheet) })
                        },
                        sheetContainerColor = DarkGray,
                        sheetPeekHeight = 30.dp
                    ) {}
                }
            }
        }
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

*/
