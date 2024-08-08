package com.example.saybettereducator.ui.view.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.saybettereducator.ui.common.showErrorSnackBar
import com.example.saybettereducator.ui.intent.UserInfoIntent
import com.example.saybettereducator.ui.model.UserInfoState
import com.example.saybettereducator.ui.sideeffect.UserInfoSideEffect
import com.example.saybettereducator.ui.theme.SaybetterEducatorTheme
import com.example.saybettereducator.ui.view.components.NameInputBox
import com.example.saybettereducator.ui.view.components.ProfileImageCard
import com.example.saybettereducator.ui.view.components.ProfilePopup
import com.example.saybettereducator.ui.view.components.TitleText
import com.example.saybettereducator.ui.view.components.UserInfoBottomBar
import com.example.saybettereducator.ui.view.components.UserInfoTopAppBar
import com.example.saybettereducator.ui.viewmodel.UserInfoViewModel
import com.example.saybettereducator.utils.image.CameraUtil
import com.example.saybettereducator.utils.image.GalleryUtil
import kotlinx.coroutines.CoroutineScope
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect


@Composable
fun UserInfoScreen(navController: NavController, viewModel: UserInfoViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val viewState by viewModel.collectAsState()

    viewModel.collectSideEffect {
        when (it) {
            is UserInfoSideEffect.NavigateHome -> navController.navigate("home")
            is UserInfoSideEffect.NavigateLoginSuccess -> navController.navigate("loginSuccessScreen")
            is UserInfoSideEffect.NetworkError ->
                coroutineScope.showErrorSnackBar(snackbarHostState) {
                    viewModel.handleIntent(UserInfoIntent.Refresh)
                }
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { viewModel.handleIntent(UserInfoIntent.OnGalleryPictureTaken(it)) }
    }
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { uri ->
        uri.let { viewModel.handleIntent(UserInfoIntent.OnCameraPictureTaken) }
    }

    val galleryUtil = remember { GalleryUtil(context) }
    val cameraUtil = remember { CameraUtil(context) }

    UserInfoScreen(
        state = viewState,
        snackbarHostState = snackbarHostState,
        galleryUtil = galleryUtil,
        cameraUtil = cameraUtil,
        galleryLauncher = galleryLauncher,
        cameraLauncher = cameraLauncher,
        action = viewModel::handleIntent
    )
}

@Composable
private fun UserInfoScreen(
    state: UserInfoState,
    snackbarHostState: SnackbarHostState,
    galleryUtil: GalleryUtil,
    cameraUtil: CameraUtil,
    galleryLauncher: ActivityResultLauncher<String>,
    cameraLauncher: ActivityResultLauncher<Uri>,
    action: (UserInfoIntent) -> Unit
) {
    val lazyListState = rememberLazyListState()

    Scaffold(
        topBar = { UserInfoTopAppBar() },
        bottomBar = {
            UserInfoBottomBar("다음") {
                action(UserInfoIntent.NavigateLoginSuccess)
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { innerPadding ->
        Box(Modifier.fillMaxWidth()) {
            LazyColumn(
                state = lazyListState,
                contentPadding = innerPadding,
                modifier = Modifier
                    .padding(start = 16.dp, top = 20.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                item {
                    Box(modifier = Modifier.padding(bottom = 50.dp)) {
                        TitleText(
                            title = "로그인에 성공했어요!", subtitle = "시작하기 전 기본 설정이 필요해요."
                        )
                    }
                }

                item {
                    ProfileImageCard(state.profileImageUrl) {
                        action(UserInfoIntent.ShowPopup(true))
                    }
                }

                item {
                    Column(modifier = Modifier.padding(top = 50.dp)) {
                        NameInputBox(state.name) {
                            action(UserInfoIntent.UpdateName(it))
                        }
                    }
                }

            }
        }
    }

    ProfilePopup(
        state.showPopup,
        { action(UserInfoIntent.ShowPopup(it)) },
        { action(UserInfoIntent.UpdateProfileImage(it)) },
        { cameraUtil.openCamera(cameraLauncher) },
        { galleryUtil.openGallery(galleryLauncher) }
    )
}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4A
)
@Composable
fun UserInfoScreenPreview() {
    val navController = rememberNavController()
    SaybetterEducatorTheme {
        UserInfoScreen(
            navController = navController,
        )
    }
}

