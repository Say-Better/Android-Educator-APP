package com.example.saybettereducator.ui.view.components.userinfo

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import coil.compose.rememberAsyncImagePainter
import com.example.saybettereducator.R
import com.example.saybettereducator.ui.theme.Black
import com.example.saybettereducator.ui.theme.Gray
import com.example.saybettereducator.ui.theme.GrayW40
import com.example.saybettereducator.ui.theme.PretendardTypography
import com.example.saybettereducator.ui.theme.White

@Composable
fun ProfilePopup(
    showPopupState: Boolean,
    onShowPopup: (Boolean) -> Unit,
    onProfileImageChange: (Uri) -> Unit,
    onOpenCamera: () -> Unit,
    onOpenGallery: () -> Unit
) {
    PopupBox(
        popupWidth = 328f,
        popupHeight = 256f,
        showPopup = showPopupState,
        onClickOutside = { onShowPopup(false) }
    ) {
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
    onOpenCamera: () -> Unit,
    onOpenGallery: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 17.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PopupOptionItem("카메라로 촬영", onShowPopup) {
            onOpenCamera()
        }
        Divider()
        PopupOptionItem("갤러리에서 선택", onShowPopup) {
            onOpenGallery()
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