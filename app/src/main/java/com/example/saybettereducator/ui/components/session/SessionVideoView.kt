package com.example.saybettereducator.ui.components.session

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.saybettereducator.R
import com.example.saybettereducator.data.service.MainService
import com.example.saybettereducator.ui.model.ProgressState
import com.example.saybettereducator.ui.model.ResponseFilterType
import com.example.saybettereducator.ui.model.SessionState
import com.example.saybettereducator.ui.theme.Gray5B25
import com.example.saybettereducator.ui.theme.Gray5B50
import com.example.saybettereducator.ui.theme.MainGreen
import com.example.saybettereducator.ui.theme.MainGreen_60
import com.example.saybettereducator.ui.theme.Red
import com.example.saybettereducator.ui.theme.Red_60
import com.example.saybettereducator.ui.theme.White
import com.example.saybettereducator.ui.theme.pretendardRegularFont
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SessionVideoView(
    isDisplayReady: Boolean,
    sessionState: SessionState,
    progressState: ProgressState
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .size(
                width = if (isDisplayReady) 328.dp else 263.dp,
                height = if (isDisplayReady) 404.dp else 75.dp
            )
    ) {
        // educator cam
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(
                    width = if (isDisplayReady) 328.dp else 128.dp,
                    height = if (isDisplayReady) 196.dp else 75.dp
                )
                .background(
                    color = Color.DarkGray,
                    shape = RoundedCornerShape(if (isDisplayReady) 16.dp else 8.dp)
                )
                .align(Alignment.TopStart)
        ) {
            LocalVideoRenderer(
                modifier = Modifier
                    .size(
                        width = if (isDisplayReady) 328.dp else 128.dp,
                        height = if (isDisplayReady) 196.dp else 75.dp
                    )
                    .clip(RoundedCornerShape(if (isDisplayReady) 16.dp else 8.dp))
            )
            if (sessionState.greetState) {
                var targetRotation by remember { mutableFloatStateOf(0f) }

                val rotationAnimation by animateFloatAsState(
                    targetValue = targetRotation,
                    animationSpec = tween(durationMillis = 200), label = ""
                )

                LaunchedEffect(Unit) {
                    while (true) { // 상태가 true일 때 계속 반복
                        targetRotation = 5f // 오른쪽으로 회전
                        delay(200)
                        targetRotation = -5f // 왼쪽으로 회전
                        delay(200)
                    }
                    targetRotation = 0f // 원래 위치로 돌아옴
                }


                Box(
                    modifier = Modifier
                        .size(
                            width = if (isDisplayReady) 328.dp else 128.dp,
                            height = if (isDisplayReady) 196.dp else 75.dp
                        )
                        .clip(RoundedCornerShape(if (isDisplayReady) 16.dp else 8.dp))
                        .background(Gray5B50)
                        .graphicsLayer(
                            rotationZ = rotationAnimation
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_hello),
                        contentDescription = "Hello Image",
                        modifier = Modifier.align(Alignment.Center).size(64.dp)
                    )
                }
            }
        }


        // learner cam
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(
                    width = if (isDisplayReady) 328.dp else 128.dp,
                    height = if (isDisplayReady) 196.dp else 75.dp
                )
                .background(
                    color = Color.DarkGray,
                    shape = RoundedCornerShape(if (isDisplayReady) 16.dp else 8.dp)
                )
                .align(if (isDisplayReady) Alignment.BottomEnd else Alignment.TopEnd)
        ) {
            if (sessionState.isPeerConnected){
                RemoteVideoRenderer(
                    modifier = Modifier
                        .size(
                            width = if (isDisplayReady) 328.dp else 128.dp,
                            height = if (isDisplayReady) 196.dp else 75.dp
                        )
                        .clip(RoundedCornerShape(if (isDisplayReady) 16.dp else 8.dp))
                )
                if (sessionState.remoteGreetState) {
                    var targetRotation by remember { mutableFloatStateOf(0f) }

                    val rotationAnimation by animateFloatAsState(
                        targetValue = targetRotation,
                        animationSpec = tween(durationMillis = 200), label = ""
                    )

                    LaunchedEffect(Unit) {
                        while (true) { // 상태가 true일 때 계속 반복
                            targetRotation = 5f // 오른쪽으로 회전
                            delay(200)
                            targetRotation = -5f // 왼쪽으로 회전
                            delay(200)
                        }
                        targetRotation = 0f // 원래 위치로 돌아옴
                    }


                    Box(
                        modifier = Modifier
                            .size(
                                width = if (isDisplayReady) 328.dp else 128.dp,
                                height = if (isDisplayReady) 196.dp else 75.dp
                            )
                            .clip(RoundedCornerShape(if (isDisplayReady) 16.dp else 8.dp))
                            .background(Gray5B50)
                            .graphicsLayer(
                                rotationZ = rotationAnimation
                            )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_hello),
                            contentDescription = "Hello Image",
                            modifier = Modifier.align(Alignment.Center).size(64.dp)
                        )
                    }
                }

                if (progressState.responseFilter != ResponseFilterType.NONE) {
                    Box(
                        modifier = Modifier
                            .size(
                                width = if (isDisplayReady) 328.dp else 128.dp,
                                height = if (isDisplayReady) 196.dp else 75.dp
                            )
                            .clip(RoundedCornerShape(if (isDisplayReady) 16.dp else 8.dp))
                            .background(if(progressState.responseFilter == ResponseFilterType.YES) MainGreen_60 else Red_60)
                    ) {
                        Text(
                            text = if(progressState.responseFilter == ResponseFilterType.YES) "예" else "아니오",
                            fontSize = 18.sp,
                            fontFamily = FontFamily(pretendardRegularFont),
                            fontWeight = FontWeight(600),
                            color = White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LocalVideoRenderer(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = {
            MainService.localSurfaceView!!
        },
        update = { surfaceViewRenderer ->
            // SurfaceViewRenderer 업데이트 로직 (필요한 경우)
        }
    )
}

@Composable
fun RemoteVideoRenderer(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = {
            MainService.remoteSurfaceView!!
        },
        update = { surfaceViewRenderer ->
            // SurfaceViewRenderer 업데이트 로직 (필요한 경우)
        }
    )
}