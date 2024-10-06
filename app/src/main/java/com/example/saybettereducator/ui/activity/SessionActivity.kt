package com.example.saybettereducator.ui.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.saybettereducator.data.api.helper.WebRTCClient
import com.example.saybettereducator.data.repository.MainServiceRepository
import com.example.saybettereducator.data.service.MainService
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
import com.example.saybettereducator.ui.sideeffect.ProgressSideEffect
import com.example.saybettereducator.ui.sideeffect.SessionSideEffect
import com.example.saybettereducator.ui.theme.BottomBar
import com.example.saybettereducator.ui.theme.DarkGray
import com.example.saybettereducator.ui.viewmodel.ProgressViewModel
import com.example.saybettereducator.ui.viewmodel.SessionViewModel
import com.example.saybettereducator.utils.InstantInteractionType
import com.example.saybettereducator.utils.InstantInteractionType.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import org.webrtc.SurfaceViewRenderer
import javax.inject.Inject

@AndroidEntryPoint
class SessionActivity: ComponentActivity(), MainService.EndCallListener {

    private var iconState by mutableStateOf(false) // 이 부분을 추가해주세요.

    private var target: String? = null
    private var isCaller: Boolean = true

    @Inject lateinit var webRTCClient: WebRTCClient
    @Inject lateinit var serviceRepository: MainServiceRepository

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setContent {
            SessionScreen()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun init() {
        intent.getStringExtra("target")?.let {
            this.target = it
        }?: kotlin.run {
            finish()
        }

        // MainService에서 SurfaceView를 관리하도록 위임
        MainService.localSurfaceView = SurfaceViewRenderer(this)
        MainService.remoteSurfaceView = SurfaceViewRenderer(this)

        isCaller = intent.getBooleanExtra("isCaller", true)

        // Activity에 표시될 SurfaceViewRenderer를 MainService 멤버변수에 연결하고 serviceRepo를 통해 초기화하도록 명령
        serviceRepository.setupViews(isCaller, target!!)

        MainService.endCallListener = this
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun SessionScreen(
        sessionViewModel: SessionViewModel = hiltViewModel(),
        progressViewModel: ProgressViewModel = hiltViewModel()
    ) {
        val scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = rememberStandardBottomSheetState(initialValue = SheetValue.PartiallyExpanded)
        )
        val scope = rememberCoroutineScope()

        val isCameraOn by remember {
            mutableStateOf(false)
        }
        val sessionViewState by sessionViewModel.collectAsState()
        val progressViewState by progressViewModel.collectAsState()

        sessionViewModel.collectSideEffect {
            when(it) {
                is SessionSideEffect.PeerConnectionSuccess -> sessionViewModel.handleIntent(SessionIntent.OnRemoteViewReady)
            }
        }

        // SideEffect 처리
        progressViewModel.collectSideEffect { sideEffect ->
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

        // 바텀시트 상태 변화를 감지하여 뷰모델에 알림
        LaunchedEffect(scaffoldState.bottomSheetState.currentValue) {
            when (scaffoldState.bottomSheetState.currentValue) {
                SheetValue.Expanded -> {
                    if (!progressViewState.isBottomSheetOpen) {
                        progressViewModel.handleIntent(ProgressIntent.ToggleBottomSheet)
                    }
                }
                SheetValue.PartiallyExpanded -> {
                    if (progressViewState.isBottomSheetOpen) {
                        progressViewModel.handleIntent(ProgressIntent.ToggleBottomSheet)
                    }
                }
                else -> { /* No action needed */ }
            }
        }

        BackHandler {
            serviceRepository.sendEndCall()
        }

        SessionScreen(
            sessionState = sessionViewState,
            progressState = progressViewState,
            scaffoldState = scaffoldState,
            onSessionIntent = sessionViewModel::handleIntent,
            onProgressIntent = progressViewModel::handleIntent,
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun SessionScreen(
        sessionState: SessionState,
        progressState: ProgressState,
        scaffoldState: BottomSheetScaffoldState,
        onSessionIntent: (SessionIntent) -> Unit,
        onProgressIntent: (ProgressIntent) -> Unit,
    )  {
        Scaffold(
            topBar = {
                if (!sessionState.isStart) ReadyTopBar(serviceRepository)
                else ProgressTopBar(isPlaying = progressState.isVoicePlaying)
            },
            bottomBar = {
                SessionBottomBar(
                    sessionState.isStart,
                    serviceRepository,
                    onStartSolution = { onSessionIntent(SessionIntent.StartProgress) }
                )
            }
        ) { innerPadding ->
            Surface(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                color = Color.Black
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
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

                    // 교육자, 학습자 비디오 화면
                    SessionVideoView(sessionState = sessionState, progressState = progressState)
                    Spacer(modifier = Modifier.weight(1f))

                    // 인사 버튼
                    if(!sessionState.isStart) {
                        ReadyHelloBtn(
                            greetState = sessionState.greetState,
                            onHelloClick = { onSessionIntent(SessionIntent.HelloClicked) }
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }

                // 세션 진행 시 바텀시트
                if(sessionState.isStart) {
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
    fun VideoCallViewPreview() {
        SessionScreen()
    }

    override fun onCallEnded() {
        finish()
    }

}