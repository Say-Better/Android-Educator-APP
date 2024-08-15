package com.example.saybettereducator.ui.components.session

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.saybettereducator.data.service.MainService
import com.example.saybettereducator.ui.model.SessionState

@Composable
fun SessionVideoView(sessionState: SessionState) {
    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .size(
                width = if (!sessionState.isStart) 328.dp else 263.dp,
                height = if (!sessionState.isStart) 404.dp else 75.dp
            )
    ) {
        // educator cam
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(
                    width = if (!sessionState.isStart) 328.dp else 128.dp,
                    height = if (!sessionState.isStart) 196.dp else 75.dp
                )
                .background(
                    color = Color.DarkGray,
                    shape = RoundedCornerShape(if (!sessionState.isStart) 16.dp else 8.dp)
                )
                .align(Alignment.TopStart)
        ) {
            LocalVideoRenderer(
                modifier = Modifier
                    .size(
                        width = if (!sessionState.isStart) 328.dp else 128.dp,
                        height = if (!sessionState.isStart) 196.dp else 75.dp
                    )
                    .clip(RoundedCornerShape(if (!sessionState.isStart) 16.dp else 8.dp))
            )
        }


        // learner cam
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(
                    width = if (!sessionState.isStart) 328.dp else 128.dp,
                    height = if (!sessionState.isStart) 196.dp else 75.dp
                )
                .background(
                    color = Color.DarkGray,
                    shape = RoundedCornerShape(if (!sessionState.isStart) 16.dp else 8.dp)
                )
                .align(if (!sessionState.isStart) Alignment.BottomEnd else Alignment.TopEnd)
        ) {
            if (sessionState.isPeerConnected){
                RemoteVideoRenderer(
                    modifier = Modifier
                        .size(
                            width = if (!sessionState.isStart) 328.dp else 128.dp,
                            height = if (!sessionState.isStart) 196.dp else 75.dp
                        )
                        .clip(RoundedCornerShape(if (!sessionState.isStart) 16.dp else 8.dp))
                )
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