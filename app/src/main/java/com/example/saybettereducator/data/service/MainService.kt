package com.example.saybettereducator.data.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.saybettereducator.domain.model.DataModel
import com.example.saybettereducator.data.repository.MainRepository
import com.example.saybettereducator.data.model.MainServiceActions.*
import dagger.hilt.android.AndroidEntryPoint
import org.webrtc.SurfaceViewRenderer
import javax.inject.Inject

@AndroidEntryPoint
class MainService : Service(), MainRepository.Listener {
    val TAG : String = "MainService"

    private var isServiceRunning = false
    private var userid : String? = null

    private lateinit var notificationManager : NotificationManager

    @Inject
    lateinit var mainRepository : MainRepository

    companion object {
        var listener : CallEventListener? = null
        var endCallListener : EndCallListener? = null
        var localSurfaceView : SurfaceViewRenderer? = null
        var remoteSurfaceView : SurfaceViewRenderer? = null
    }

    private fun handleSwitchCamera() {
        mainRepository.switchCamera()
    }

    //생성되면 NotificationManager 가져오기
    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(
            NotificationManager::class.java
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let { incomingIntent ->
            when(incomingIntent.action) {
                START_SERVICE.name -> handleStartService(incomingIntent)
                SETUP_VIEWS.name -> handleSetupViews(incomingIntent)
                END_CALL.name -> handleEndCall()
                SWITCH_CAMERA.name -> handleSwitchCamera()
                TOGGLE_AUDIO.name -> handleToggleAudio(incomingIntent)
                TOGGLE_VIDEO.name -> handleToggleVideo(incomingIntent)
                else -> Unit
            }
        }
        return START_STICKY
    }

    private fun handleToggleVideo(incomingIntent: Intent) {
        val shouldBeMuted = incomingIntent.getBooleanExtra("shouldBeMuted", true)
        mainRepository.toggleVideo(shouldBeMuted)
    }

    private fun handleToggleAudio(incomingIntent: Intent) {
        val shouldBeMuted = incomingIntent.getBooleanExtra("shouldBeMuted", true)
        mainRepository.toggleAudio(shouldBeMuted)
    }

    private fun handleSetupViews(incomingIntent: Intent) {
        val isCaller = incomingIntent.getBooleanExtra("isCaller", false)
        val target = incomingIntent.getStringExtra("target")

        mainRepository.setTarget(target!!)

        // Local, Remote SurfaceViewRenderer init
        mainRepository.initLocalSurfaceView(localSurfaceView!!)
        mainRepository.initRemoteSurfaceView(remoteSurfaceView!!)

        //Caller 가 아닐 경우 Call 시작
        if(!isCaller) {
            Log.d("MainService", "Start Call to $target")
            mainRepository.startCall()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleStartService(incomingIntent: Intent) {
        //Service 시작 시점에서 toggle on
        if(!isServiceRunning) {
            isServiceRunning = true
            userid = incomingIntent.getStringExtra("userid")
            startServiceWithNotification()

            //setup my clients
            mainRepository.listener = this
            mainRepository.initFirebase()
            mainRepository.initWebrtcClient(userid!!)
        }
    }

    //Notification Manager에게 정의한 notificationChannel 전달하여 생성하기
    @RequiresApi(Build.VERSION_CODES.O)
    private fun startServiceWithNotification() {
        val notificationChannel = NotificationChannel(
            "channel1", "foreground", NotificationManager.IMPORTANCE_HIGH
        )

        notificationManager.createNotificationChannel(notificationChannel)
        val notification = NotificationCompat.Builder(
            this, "channel1"
        )

        startForeground(1, notification.build())
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    override fun onLatestEventReceived(data: DataModel) {
        Log.d(TAG, "onLatestEventReceived: $data")
        listener?.onCallReceived(data)

    }

    private fun handleEndCall() {
        //1. we have to send a signal to other peer that call is ended
        mainRepository.sendEndCall()

        //2. end out call process and restart our webrtc client
        endCallAndRestartRepository()
    }

    override fun endCall() {
        //remote peer로부터 통화 종료 신호를 받은 경우
        endCallAndRestartRepository()
    }

    //MainActivity에서 구현됨
    interface CallEventListener {
        fun onCallReceived(model: DataModel)
    }

    private fun endCallAndRestartRepository() {
        mainRepository.endCall()
        endCallListener?.onCallEnded()
        mainRepository.initWebrtcClient(userid!!)
    }

    interface EndCallListener {
        fun onCallEnded()
    }


}