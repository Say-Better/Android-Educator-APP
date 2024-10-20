package com.example.saybettereducator.data.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PROJECTION
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.saybettereducator.data.model.DataModel
import com.example.saybettereducator.data.repository.MainRepository
import com.example.saybettereducator.data.model.MainServiceActions.*
import com.example.saybettereducator.utils.DataConverter
import com.example.saybettereducator.utils.InstantInteractionType.*
import com.example.saybettereducator.utils.RTCAudioManager
import dagger.hilt.android.AndroidEntryPoint
import org.webrtc.DataChannel
import org.webrtc.SurfaceViewRenderer
import javax.inject.Inject

@AndroidEntryPoint
class MainService : Service(), MainRepository.Listener {
    val TAG : String = "MainService"

    private var isServiceRunning = false
    private var userid : String? = null

    private lateinit var notificationManager : NotificationManager
    private lateinit var rtcAudioManager : RTCAudioManager
    private var isPreviousCallStateVideo = true

    @Inject
    lateinit var mainRepository : MainRepository

    companion object {
        var listener : CallEventListener? = null
        var sessionInteractionListener : SessionInteractionListener? = null
        var progressInteractionListener : ProgressInteractionListener? = null
        var endCallListener : EndCallListener? = null
        var localSurfaceView : SurfaceViewRenderer? = null
        var remoteSurfaceView : SurfaceViewRenderer? = null
        var screenPermissionIntent : Intent? = null
    }

    private fun handleSwitchCamera() {
        mainRepository.switchCamera()
    }

    //생성되면 NotificationManager 가져오기
    override fun onCreate() {
        super.onCreate()

        rtcAudioManager = RTCAudioManager.create(this)
        rtcAudioManager.setDefaultAudioDevice(RTCAudioManager.AudioDevice.SPEAKER_PHONE)

        notificationManager = getSystemService(
            NotificationManager::class.java
        )
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let { incomingIntent ->
            when(incomingIntent.action) {
                START_SERVICE.name -> handleStartService(incomingIntent)
                SETUP_VIEWS.name -> handleSetupViews(incomingIntent)
                END_CALL.name -> handleEndCall()
                SWITCH_CAMERA.name -> handleSwitchCamera()
                TOGGLE_AUDIO.name -> handleToggleAudio(incomingIntent)
                TOGGLE_VIDEO.name -> handleToggleVideo(incomingIntent)
                STOP_SERVICE.name -> handleStopService()
                TOGGLE_SCREEN_SHARE.name -> handleToggleScreenShare(incomingIntent)
                else -> Unit
            }
        }
        return START_STICKY
    }

    private fun handleToggleScreenShare(incomingIntent: Intent) {
        val isStarting = incomingIntent.getBooleanExtra("isStarting", true)
        Log.d("screen-share", "[MainService] handleToggleScreenShare 진입, isStarting $isStarting")
        if(isStarting){
            // we should start screen share
            // but we have to keep it in mind that we first should remove the camera streaming first
            if (isPreviousCallStateVideo) {
                mainRepository.toggleVideo(true)
            }
            Log.d("screen-share", "Local View Off")
            mainRepository.setScreenCaptureIntent(screenPermissionIntent!!)
            mainRepository.toggleScreenShare(true)
        } else {
            // we should stop screen share and check if camera streaming was on so we should make it on back again
            mainRepository.toggleScreenShare(false)
            if (isPreviousCallStateVideo) {
                mainRepository.toggleVideo(false)
            }
        }
    }

    private fun handleStopService() {
        mainRepository.endCall()
        mainRepository.logOff {
            isServiceRunning = false
            stopSelf()
        }
    }

    private fun handleToggleVideo(incomingIntent: Intent) {
        val shouldBeMuted = incomingIntent.getBooleanExtra("shouldBeMuted", true)
        this.isPreviousCallStateVideo = !shouldBeMuted
        mainRepository.toggleVideo(shouldBeMuted)
    }

    private fun handleToggleAudio(incomingIntent: Intent) {
        val shouldBeMuted = incomingIntent.getBooleanExtra("shouldBeMuted", true)
        mainRepository.toggleAudio(shouldBeMuted)
    }

    private fun handleSetupViews(incomingIntent: Intent) {
        val isCaller = incomingIntent.getBooleanExtra("isCaller", false)
        val target = incomingIntent.getStringExtra("target")
        val isVideoCall = true

        this.isPreviousCallStateVideo = isVideoCall

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

    @RequiresApi(Build.VERSION_CODES.Q)
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
    @RequiresApi(Build.VERSION_CODES.Q)
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

    override fun onDataReceivedFromChannel(it: DataChannel.Buffer) {
        Log.d("DataChannel", "Data Received")

        //여기서 case 나누어 처리하기
        val model = DataConverter.convertToModel(it)
        model?.let {
            if (it.first == "TEXT") {
                when(it.second) {
                    GREETING.name -> {
                        sessionInteractionListener?.onGreeting()
                    }
                    else -> {
                        Log.d("DataChannel", it.second.toString())
                        if(it.second.toString().contains(SYMBOL_HIGHLIGHT.name)) {
                            val chunkedMessage: List<String> = it.second.toString().split(' ')
                            val action: String = chunkedMessage[0]
                            val symbolId: Int = chunkedMessage[1].toInt()

                            progressInteractionListener?.onSymbolHighlight(symbolId)
                        } else {
                            sessionInteractionListener?.onReceiveChatting(it.second.toString())
                        }

                    }
                }
            } else {
                Toast.makeText(this, "received data is wrong", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDataChannelReceived() {
        //DataChannel 감지한 경우
        Log.d("DataChannel", "Receive Data Channel")
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

    interface SessionInteractionListener {
        fun onReceiveChatting(msg: String)
        fun onGreeting()
        fun onSwitchToLearning()
    }

    interface ProgressInteractionListener {
        fun onSymbolHighlight(symbolId: Int)
    }

}