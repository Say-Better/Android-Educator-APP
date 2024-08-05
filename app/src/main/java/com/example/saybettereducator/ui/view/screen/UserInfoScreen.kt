package com.example.saybettereducator.ui.view.screen

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.saybettereducator.R
import com.example.saybettereducator.ui.intent.UserInfoIntent
import com.example.saybettereducator.ui.model.UserInfoState
import com.example.saybettereducator.ui.theme.Black
import com.example.saybettereducator.ui.theme.Gray
import com.example.saybettereducator.ui.theme.GrayW40
import com.example.saybettereducator.ui.theme.PretendardTypography
import com.example.saybettereducator.ui.theme.White
import com.example.saybettereducator.ui.view.components.BottomBar
import com.example.saybettereducator.ui.view.components.TitleText
import com.example.saybettereducator.ui.view.components.TopBar
import com.example.saybettereducator.ui.viewmodel.UserInfoViewModel

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun UserInfoScreenPreview() {
    val navController = rememberNavController()
    UserInfoScreen(navController, UserInfoViewModel())
}

@Composable
fun UserInfoScreen(navController: NavController, viewModel: UserInfoViewModel) {
    val state by viewModel.state.collectAsState()

    Scaffold(topBar = { TopBar() },
        bottomBar = { BottomBar(navController, "loginSuccessScreen", "다음") }) { innerPadding ->
        MainContent(
            innerPadding,
            state,
            { viewModel.processIntent(UserInfoIntent.UpdateName(it)) },
            { viewModel.processIntent(UserInfoIntent.UpdateShowPopup(it)) }
        )
    }

    ProfilePopup(state.showPopup,
        { viewModel.processIntent(UserInfoIntent.UpdateShowPopup(it)) },
        { viewModel.processIntent(UserInfoIntent.UpdateProfileImage(it)) },
        { viewModel.processIntent(UserInfoIntent.UpdateOpenCamera(it)) },
        { viewModel.processIntent(UserInfoIntent.UpdateOpenGallery(it)) })
}

@Composable
private fun ProfilePopup(
    showPopupState: Boolean,
    onShowPopup: (Boolean) -> Unit,
    onProfileImageChange: (Uri) -> Unit,
    onOpenCamera: (Boolean) -> Unit,
    onOpenGallery: (Boolean) -> Unit
) {
    PopupBox(popupWidth = 328f,
        popupHeight = 256f,
        showPopup = showPopupState,
        onClickOutside = { onShowPopup(false) }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            PopupHeader(onShowPopup)
            PopupOptions(onShowPopup, onProfileImageChange, onOpenCamera, onOpenGallery)
        }
    }
}

@Composable
private fun PopupOptions(
    onShowPopup: (Boolean) -> Unit,
    onProfileImageChange: (Uri) -> Unit,
    onOpenCamera: (Boolean) -> Unit,
    onOpenGallery: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 17.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PopupOptionItem("카메라로 촬영", onShowPopup) {
            onOpenCamera(true)
        }
        Divider()
        PopupOptionItem("갤러리에서 선택", onShowPopup) {
            onOpenGallery(true)
        }
        Divider()
        PopupOptionItem("기본 이미지 사용", onShowPopup) {
            onProfileImageChange(
                Uri.parse("android.resource://com.example.saybettereducator/drawable/educator_profile")
            )
        }
    }
}

@Composable
private fun Divider() {
    Box(
        Modifier
            .border(
                width = 1.dp, color = Color.Black.copy(alpha = 0.3f)
            )
            .padding(0.5.dp)
            .width(280.dp)
    ) {}
}

@Composable
private fun PopupOptionItem(
    text: String, onShowPopup: (Boolean) -> Unit, onClick: () -> Unit
) {
    Box(
        modifier = Modifier.height(60.dp), contentAlignment = Alignment.Center
    ) {
        Text(text = text,
            style = PretendardTypography.bodyLarge.copy(Black),
            modifier = Modifier.clickable {
                onClick()
                onShowPopup(false)
            })
    }
}

@Composable
private fun PopupHeader(onShowPopup: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(modifier = Modifier.size(24.dp)) {}

        Text(
            text = "프로필 설정",
            style = PretendardTypography.headlineMedium.copy(Gray),
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        Image(
            painter = painterResource(R.drawable.cancel_button_2),
            contentDescription = "cancel button",
            contentScale = ContentScale.None,
            modifier = Modifier
                .size(24.dp)
                .align(Alignment.CenterVertically)
                .clickable(onClick = { onShowPopup(false) }),
        )
    }
}

@Composable
private fun MainContent(
    innerPadding: PaddingValues,
    state: UserInfoState,
    onNameChange: (String) -> Unit,
    onShowPopup: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(start = 16.dp, top = 20.dp, end = 16.dp)
            .fillMaxWidth()
    ) {
        Box(modifier = Modifier.padding(bottom = 50.dp)) {
            TitleText(
                title = "로그인에 성공했어요!", subtitle = "시작하기 전 기본 설정이 필요해요."
            )
        }

        Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            ProfileImageCard(state.profileImageUrl, onShowPopup)
        }

        Column(modifier = Modifier.padding(top = 50.dp)) {
            NameInputBox(state.name, onNameChange)
        }
    }
}

@Composable
private fun NameInputBox(
    nameState: String, onNameChange: (String) -> Unit
) {
    Text(
        text = "이름",
        style = PretendardTypography.bodySmall.copy(Gray),
        modifier = Modifier.padding(bottom = 12.dp)
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .border(
                width = 1.dp, color = Black, shape = RoundedCornerShape(size = 12.dp)
            )
            .fillMaxWidth()
            .height(48.dp)
    ) {
        BasicTextField(
            value = nameState,
            onValueChange = { onNameChange(it) },
            textStyle = PretendardTypography.bodyMedium.copy(Black),
            singleLine = true,
            maxLines = 1,
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f),
        )

        Image(painter = painterResource(id = R.drawable.cancel_button),
            contentDescription = "cancell button",
            contentScale = ContentScale.None,
            modifier = Modifier
                .padding(end = 16.dp)
                .align(Alignment.CenterVertically)
                .clickable { onNameChange("교육자") })
    }
}

@Composable
fun ProfileImageCard(
    profileImageUri: Uri?, onShowPopup: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(0.dp)
            .size(176.dp)
    ) {
        profileImageUri?.let { uri ->
            Image(
                painter = rememberAsyncImagePainter(uri),
                contentDescription = "Profile Image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .border(
                        width = 1.dp, color = GrayW40, shape = RoundedCornerShape(size = 24.dp)
                    )
                    .size(160.dp)
                    .clip(RoundedCornerShape(size = 24.dp))
            )
        } ?: Image(
            painter = painterResource(id = R.drawable.educator_profile),
            contentDescription = "Educator Profile Image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .border(
                    width = 1.dp, color = GrayW40, shape = RoundedCornerShape(size = 24.dp)
                )
                .size(160.dp)
                .clip(RoundedCornerShape(size = 24.dp))
        )

        Image(painter = painterResource(id = R.drawable.profile_button),
            contentDescription = "Profile Button",
            contentScale = ContentScale.None,
            modifier = Modifier
                .padding(0.dp)
                .size(44.dp)
                .align(Alignment.BottomEnd)
                .clip(RoundedCornerShape(50))
                .clickable { onShowPopup(true) })
    }
}

@Composable
private fun PopupBox(
    popupWidth: Float,
    popupHeight: Float,
    popupRadius: Float = 16f,
    backgroundColor: Color = White,
    showPopup: Boolean,
    onClickOutside: () -> Unit,
    content: @Composable () -> Unit
) {
    if (showPopup) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Black.copy(alpha = 0.2f))
                .blur(
                    radius = 10.dp, edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(8.dp))
                )
        ) {
            Popup(
                alignment = Alignment.Center,
                onDismissRequest = { onClickOutside() },
            ) {
                Box(
                    modifier = Modifier
                        .width(popupWidth.dp)
                        .height(popupHeight.dp)
                        .background(
                            backgroundColor, shape = RoundedCornerShape(popupRadius.dp)
                        )
                        .clip(RoundedCornerShape(popupRadius.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    content()
                }
            }
        }
    }
}