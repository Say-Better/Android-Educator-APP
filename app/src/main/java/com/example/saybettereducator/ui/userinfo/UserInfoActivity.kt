package com.example.saybettereducator.ui.userinfo

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.saybettereducator.R
import com.example.saybettereducator.ui.MainActivity
import com.example.saybettereducator.ui.theme.Black
import com.example.saybettereducator.ui.theme.Gray
import com.example.saybettereducator.ui.theme.GrayW40
import com.example.saybettereducator.ui.theme.MainGreen
import com.example.saybettereducator.ui.theme.PretendardTypography
import com.example.saybettereducator.ui.theme.White
import com.example.saybettereducator.ui.theme.montserratFont
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

class UserInfoActivity : ComponentActivity() {
    private lateinit var currentPhotoPath: String
    private lateinit var photoUri: Uri

    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                profileImageUri.value = photoUri
            }
        }

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                profileImageUri.value = it
            }
        }

    private val profileImageUri = mutableStateOf<Uri?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "userInfoScreen") {
                composable("userInfoScreen") { UserInfoScreen(navController, profileImageUri) }
                composable("loginSuccessScreen") { LoginSuccessScreen(navController){
                    intent = Intent(this@UserInfoActivity, MainActivity::class.java)
                    intent.putExtra("userid", "testUser")
                    startActivity(intent)
                } }
            }
        }
    }

    private fun dispatchTakePictureIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                null
            }
            photoFile?.also {
                photoUri = FileProvider.getUriForFile(
                    this,
                    "com.example.saybettereducator.fileprovider",
                    it
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                takePictureLauncher.launch(photoUri)
            }
        }
    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(null)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    @Preview(showBackground = true, widthDp = 360, heightDp = 800)
    @Composable
    fun UserInfoScreenPreview() {
        val navController = rememberNavController()
        UserInfoScreen(navController, profileImageUri)
    }

    @Preview(showBackground = true, widthDp = 360, heightDp = 800)
    @Composable
    fun LoginSuccessScreenPreview() {
        val navController = rememberNavController()
        LoginSuccessScreen(navController)
    }

    @Composable
    fun LoginSuccessScreen(
        navController: NavController,
        goMainActivity: (() -> Unit)? = null
    ) {
        Scaffold(
            topBar = { TopBar() },
            bottomBar = { BottomBar(navController, "null", "시작하기", goMainActivity) }
        ) { innerPadding -> LoginSuccessContent(innerPadding) }
    }

    @Composable
    private fun LoginSuccessContent(innerPadding: PaddingValues) {
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 16.dp, top = 20.dp, end = 16.dp)
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier.padding(bottom = 24.dp)) {
                TitleText(
                    title = "모든 준비가 완료되었어요.",
                    subtitle = "원활한 앱 사용을 위해 앱 내 권한을 허용해주세요."
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = painterResource(R.drawable.login_illust_educator),
                    contentDescription = "Login Illustration",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(480.dp)
                        .clip(RoundedCornerShape(20.dp))
                )
            }
        }
    }

    @Composable
    fun UserInfoScreen(navController: NavController, profileImageUri: MutableState<Uri?>) {
        val showPopupState = remember { mutableStateOf(false) }
        val nameState = remember { mutableStateOf("교육자") }

        Scaffold(
            topBar = { TopBar() },
            bottomBar = { BottomBar(navController, "loginSuccessScreen", "다음") }
        ) { innerPadding ->
            MainContent(
                innerPadding,
                showPopupState,
                nameState,
                profileImageUri
            )
        }

        if (showPopupState.value) {
            ProfilePopup(showPopupState)
        }
    }

    @Composable
    private fun ProfilePopup(showPopupState: MutableState<Boolean>) {
        PopupBox(
            popupWidth = 328f,
            popupHeight = 256f,
            showPopup = showPopupState.value,
            onClickOutside = { showPopupState.value = false }
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                PopupHeader(showPopupState)
                PopupOptions(showPopupState)
            }
        }
    }

    @Composable
    private fun PopupOptions(showPopupState: MutableState<Boolean>) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 17.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PopupOptionItem("카메라로 촬영", showPopupState) {
                if (ContextCompat.checkSelfPermission(
                        this@UserInfoActivity,
                        Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    dispatchTakePictureIntent()
                } else {
                    requestPermissions(arrayOf(Manifest.permission.CAMERA), 1)
                }
            }
            Divider()
            PopupOptionItem("갤러리에서 선택", showPopupState) {
                pickImageLauncher.launch("image/*")
            }
            Divider()
            PopupOptionItem("기본 이미지 사용", showPopupState) {
                profileImageUri.value =
                    Uri.parse("android.resource://com.example.saybettereducator/drawable/educator_profile")
                showPopupState.value = false
            }
        }
    }

    @Composable
    private fun Divider() {
        Box(
            Modifier
                .border(
                    width = 1.dp,
                    color = Color.Black.copy(alpha = 0.3f)
                )
                .padding(0.5.dp)
                .width(280.dp)
        ) {}
    }

    @Composable
    private fun PopupOptionItem(
        text: String,
        showPopupState: MutableState<Boolean>,
        onClick: () -> Unit
    ) {
        Box(
            modifier = Modifier.height(60.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = PretendardTypography.bodyLarge.copy(Black),
                modifier = Modifier
                    .clickable {
                        onClick()
                        showPopupState.value = false
                    }
            )
        }
    }

    @Composable
    private fun PopupHeader(showPopupState: MutableState<Boolean>) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(modifier = Modifier.size(24.dp)) {}

            Text(
                text = "프로필 설정",
                style = PretendardTypography.headlineMedium.copy(Gray),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )

            Image(
                painter = painterResource(R.drawable.cancel_button_2),
                contentDescription = "cancel button",
                contentScale = ContentScale.None,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterVertically)
                    .clickable(onClick = { showPopupState.value = false }),
            )
        }
    }

    @Composable
    private fun MainContent(
        innerPadding: PaddingValues,
        showPopupState: MutableState<Boolean>,
        nameState: MutableState<String>,
        profileImageUri: MutableState<Uri?>
    ) {
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = 16.dp, top = 20.dp, end = 16.dp)
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier.padding(bottom = 50.dp)) {
                TitleText(
                    title = "로그인에 성공했어요!",
                    subtitle = "시작하기 전 기본 설정이 필요해요."
                )
            }

            Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                ProfileImageCard(showPopupState, profileImageUri)
            }

            Column(modifier = Modifier.padding(top = 50.dp)) {
                NameInputBox(nameState)
            }
        }
    }

    @Composable
    private fun BottomBar(
        navController: NavController,
        route: String,
        text: String,
        goMainActivity: (() -> Unit)? = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(88.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(16.dp)
                    .background(
                        color = MainGreen,
                        shape = RoundedCornerShape(size = 32.dp)
                    )
                    .clickable {
                        if(goMainActivity == null) { navController.navigate(route) }
                        else { goMainActivity() }
                    }
            ) {
                Text(
                    text = text,
                    style = PretendardTypography.buttonLarge.copy(White),
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    private fun TopBar() {
        Box {
            TopAppBar(
                title = {
                    Text(
                        text = "Say Better",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(montserratFont),
                        color = MainGreen,
                        modifier = Modifier
                            .padding(start = 34.04.dp, top = 15.29.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.main_logo),
                contentDescription = "main top bar logo",
                modifier = Modifier
                    .padding(start = 16.dp, top = 14.29.dp)
            )
        }
    }

    @Composable
    private fun NameInputBox(
        nameState: MutableState<String>
    ) {
        Text(
            text = "이름",
            style = PretendardTypography.bodySmall.copy(Gray),
            modifier = Modifier
                .padding(bottom = 12.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Black,
                    shape = RoundedCornerShape(size = 12.dp)
                )
                .fillMaxWidth()
                .height(48.dp)
        ) {
            BasicTextField(
                value = nameState.value,
                onValueChange = { nameState.value = it },
                textStyle = PretendardTypography.bodyMedium.copy(Black),
                singleLine = true,
                maxLines = 1,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f),
            )

            Image(
                painter = painterResource(id = R.drawable.cancel_button),
                contentDescription = "cancell button",
                contentScale = ContentScale.None,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .align(Alignment.CenterVertically)
                    .clickable { nameState.value = "" }
            )
        }
    }

    @Composable
    private fun TitleText(title: String, subtitle: String) {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                style = PretendardTypography.headlineMedium.copy(Black),
            )

            Text(
                text = subtitle,
                style = PretendardTypography.bodySmall.copy(GrayW40),
            )
        }
    }

    @Composable
    fun ProfileImageCard(
        showInputPopup: MutableState<Boolean>,
        profileImageUri: MutableState<Uri?>
    ) {
        Box(
            modifier = Modifier
                .padding(0.dp)
                .size(176.dp)
        ) {
            profileImageUri.value?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Profile Image",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = GrayW40,
                            shape = RoundedCornerShape(size = 24.dp)
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
                        width = 1.dp,
                        color = GrayW40,
                        shape = RoundedCornerShape(size = 24.dp)
                    )
                    .size(160.dp)
                    .clip(RoundedCornerShape(size = 24.dp))
            )

            Image(
                painter = painterResource(id = R.drawable.profile_button),
                contentDescription = "Profile Button",
                contentScale = ContentScale.None,
                modifier = Modifier
                    .padding(0.dp)
                    .size(44.dp)
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(50))
                    .clickable { showInputPopup.value = true }
            )
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
                        radius = 10.dp,
                        edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(8.dp))
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
                                backgroundColor,
                                shape = RoundedCornerShape(popupRadius.dp)
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
}
