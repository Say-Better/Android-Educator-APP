package com.example.saybettereducator.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.saybettereducator.domain.model.DataModel
import com.example.saybettereducator.ui.components.MainBottomNavigationBar
import com.example.saybettereducator.ui.navigation.AppNavHost
import com.example.saybettereducator.ui.theme.SaybetterEducatorTheme
import com.example.saybettereducator.ui.view.components.main.MainTopBar
import com.example.saybettereducator.utils.webrtc.repository.MainRepository
import com.example.saybettereducator.utils.webrtc.service.MainService
import com.example.saybettereducator.utils.webrtc.service.MainServiceRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity(), MainService.CallEventListener {

    data class TestDialogState(
        val isClick: Boolean = false,
        val onClickSure: () -> Unit = {},
        val onClickCancel: () -> Unit = {},
    )

    private val customAlertDialogState = mutableStateOf(TestDialogState())

    private var userid: String? = null
    private var currentReceivedModel: DataModel? = null
    private val testUser: String = "helloYI"

    // Hilt 의존성 주입
    @Inject
    lateinit var mainRepository: MainRepository
    @Inject
    lateinit var mainServiceRepository: MainServiceRepository

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("MainActivity", "onCreate")

        setContent {
            SaybetterEducatorTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = { MainTopBar() },
                    bottomBar = { MainBottomNavigationBar(navController) }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .systemBarsPadding()
                            .imePadding(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        AppNavHost(navController = navController)
                    }
                }
            }
        }

        init()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun init() {
        userid = intent.getStringExtra("userid")
        if (userid == null) finish()

        MainService.listener = this
        startMyService()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startMyService() {
        mainServiceRepository.startService(userid!!)
    }

    // Video call 클릭되었을 때
    private fun StartVideoCall(targetUserid: String, isCaller: Boolean) {
        mainRepository.sendConnectionRequest(targetUserid) {
            if (it) {
                // Video call 시작해야함
                intent = Intent(this@MainActivity, VideoCallActivity::class.java)
                intent.putExtra("target", targetUserid)
                intent.putExtra("isCaller", isCaller)
                startActivity(intent)
            }
        }
    }

    override fun onCallReceived(model: DataModel) {
        Log.d("MainService", "call receive by ${model.sender}")
        this.currentReceivedModel = model
    }

    fun resetDialogState(state: MutableState<TestDialogState>) {
        state.value = TestDialogState()
    }
}
