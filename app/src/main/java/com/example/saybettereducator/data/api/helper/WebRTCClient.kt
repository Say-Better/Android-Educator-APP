package com.example.saybettereducator.data.api.helper

import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.media.projection.MediaProjection
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import com.example.saybettereducator.data.model.DataModel
import com.example.saybettereducator.data.model.DataModelType
import com.example.saybettereducator.utils.webrtcObserver.MyPeerObserver
import com.example.saybettereducator.utils.webrtcObserver.MySdpObserver
import com.google.gson.Gson
import org.webrtc.AudioTrack
import org.webrtc.Camera2Enumerator
import org.webrtc.CameraVideoCapturer
import org.webrtc.DataChannel
import org.webrtc.DefaultVideoDecoderFactory
import org.webrtc.DefaultVideoEncoderFactory
import org.webrtc.EglBase
import org.webrtc.IceCandidate
import org.webrtc.MediaConstraints
import org.webrtc.MediaStream
import org.webrtc.PeerConnection
import org.webrtc.PeerConnectionFactory
import org.webrtc.ScreenCapturerAndroid
import org.webrtc.SessionDescription
import org.webrtc.SurfaceTextureHelper
import org.webrtc.SurfaceViewRenderer
import org.webrtc.VideoCapturer
import org.webrtc.VideoTrack
import java.nio.ByteBuffer
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WebRTCClient @Inject constructor(
    private val context: Context,
    private val gson: Gson,
) {
    val TAG = "DataChannel"
    var receiverListener : ReceiverListener?=null

    // class variables
    var listener: Listener? = null
    private lateinit var userid: String

    // webrtc variables
    private val eglBaseContext = EglBase.create().eglBaseContext
    private val peerConnectionFactory by lazy { createPeerConnectionFactory() }
    private var peerConnection: PeerConnection? = null
    private val iceServers = listOf(
        PeerConnection.IceServer.builder("stun:stun.relay.metered.ca:80").createIceServer(),
        PeerConnection.IceServer.builder("turn:global.relay.metered.ca:80")
            .setUsername("3034a2e47ead957a5246ff2d")
            .setPassword("zAyeHrbfgXr6T/sr")
            .createIceServer(),
        PeerConnection.IceServer.builder("turn:global.relay.metered.ca:80?transport=tcp")
            .setUsername("3034a2e47ead957a5246ff2d")
            .setPassword("zAyeHrbfgXr6T/sr")
            .createIceServer(),
        PeerConnection.IceServer.builder("turn:global.relay.metered.ca:443")
            .setUsername("3034a2e47ead957a5246ff2d")
            .setPassword("zAyeHrbfgXr6T/sr")
            .createIceServer(),
        PeerConnection.IceServer.builder("turns:global.relay.metered.ca:443?transport=tcp")
            .setUsername("3034a2e47ead957a5246ff2d")
            .setPassword("zAyeHrbfgXr6T/sr")
            .createIceServer()
    )
    private val localVideoSource by lazy { peerConnectionFactory.createVideoSource(false) }
    private val localAudioSource by lazy { peerConnectionFactory.createAudioSource(MediaConstraints()) }
    private val videoCapturer = getVideoCapturer(context) // Camera Video Capture를 가져오면서 Context를 전달
    private var surfaceTextureHelper : SurfaceTextureHelper? = null
    private val mediaConstraint = MediaConstraints().apply {
        mandatory.add(MediaConstraints.KeyValuePair("OfferToReceiveVideo", "true"))
        mandatory.add(MediaConstraints.KeyValuePair("OfferToReceiveAudio", "true"))
        mandatory.add(MediaConstraints.KeyValuePair("RtpDataChannels", "true"))
    }
    private val dataChannelObserver = object : DataChannel.Observer {
        override fun onBufferedAmountChange(p0: Long) {

        }

        override fun onStateChange() {
        }

        override fun onMessage(p0: DataChannel.Buffer?) {
            p0?.let { receiverListener?.onDataReceived(it) }
        }

    }

    // call variables
    private lateinit var localSurfaceView : SurfaceViewRenderer
    private lateinit var remoteSurfaceView : SurfaceViewRenderer
    private var localStream: MediaStream? = null
    private var localTrackId = ""
    private var localStreamId = ""
    private var localAudioTrack: AudioTrack? = null
    private var localVideoTrack: VideoTrack? = null

    // screen casting
    private var permissionIntent: Intent? = null
    private var screenCapturer: VideoCapturer? = null
    private val localScreenVideoSource by lazy { peerConnectionFactory.createVideoSource(false) }
    private var localScreenShareVideoTrack: VideoTrack? = null

    // installing requirements section
    init {
        initPeerConnectionFactory()
    }
    private fun initPeerConnectionFactory() {
        val options = PeerConnectionFactory.InitializationOptions.builder(context)
            .setEnableInternalTracer(true).setFieldTrials("WebRTC-H264HighProfile/Enabled/")
            .createInitializationOptions()
        PeerConnectionFactory.initialize(options)
    }
    private fun createPeerConnectionFactory() : PeerConnectionFactory {
        return PeerConnectionFactory.builder()
            .setVideoDecoderFactory(
                DefaultVideoDecoderFactory(eglBaseContext)
            ).setVideoEncoderFactory(
                DefaultVideoEncoderFactory(
                    eglBaseContext, true, true
                )
            ).setOptions(PeerConnectionFactory.Options().apply {
                disableNetworkMonitor = false
                disableEncryption = false
            }).createPeerConnectionFactory()
    }
    fun initializeWebrtcClient(
        userid: String, observer: PeerConnection.Observer,
    ){
        this.userid = userid
        localTrackId = "${userid}_track"
        localStreamId = "${userid}_stream"
        peerConnection = createPeerConnection(observer)
        createDataChannel()
    }

    private fun createDataChannel(){
        val initDataChannel = DataChannel.Init()
        val dataChannel = peerConnection?.createDataChannel("dataChannelLabel",initDataChannel)
        dataChannel?.registerObserver(dataChannelObserver)
    }

    private fun createPeerConnection(observer: PeerConnection.Observer): PeerConnection? {
        return peerConnectionFactory.createPeerConnection(iceServers, observer)
    }

    // negotiation section
    fun call(target: String) {
        peerConnection?.createOffer(object : MySdpObserver() {
            override fun onCreateSuccess(desc: SessionDescription?) {
                super.onCreateSuccess(desc)
                peerConnection?.setLocalDescription(object : MySdpObserver() {
                    override fun onSetSuccess() {
                        super.onSetSuccess()
                        listener?.onTransferEventToSocket(
                            DataModel(
                                type = DataModelType.Offer,
                                sender = userid,
                                target = target,
                                data = desc?.description
                            )
                        )
                    }
                }, desc)
            }
        }, mediaConstraint)
    }

    fun answer(target: String) {
        peerConnection?.createAnswer(object : MySdpObserver() {
            override fun onCreateSuccess(desc: SessionDescription?) {
                super.onCreateSuccess(desc)
                peerConnection?.setLocalDescription(object : MySdpObserver() {
                    override fun onSetSuccess() {
                        super.onSetSuccess()
                        listener?.onTransferEventToSocket(
                            DataModel(
                                type = DataModelType.Answer,
                                sender = userid,
                                target = target,
                                data = desc?.description
                            )
                        )
                    }
                }, desc)
            }
        }, mediaConstraint)
    }

    fun onRemoteSessionReceived(sessionDescription: SessionDescription) {
        peerConnection?.setRemoteDescription(MySdpObserver(), sessionDescription)
    }

    fun addIceCandidateToPeer(iceCandidate: IceCandidate) {
        peerConnection?.addIceCandidate(iceCandidate)
    }

    fun sendIceCandidate(target: String, iceCandidate: IceCandidate) {
        addIceCandidateToPeer(iceCandidate)
        listener?.onTransferEventToSocket(
            DataModel(
                type = DataModelType.IceCandidates,
                sender = userid,
                target = target,
                data = gson.toJson(iceCandidate)
            )
        )
    }

    fun closeConnection() {
        try {
            screenCapturer?.dispose()
            videoCapturer.dispose()
            localStream?.dispose()
            peerConnection?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun switchCamera() {
        videoCapturer.switchCamera(null)
    }

    fun toggleAudio(shouldBeMuted: Boolean) {
        if(shouldBeMuted) {
            localStream?.removeTrack(localAudioTrack)
        } else {
            localStream?.addTrack(localAudioTrack)
        }
    }

    fun toggleVideo(shouldBeMuted: Boolean) {
        if(shouldBeMuted){
            stopCapturingCamera()
        } else {
            startCapturingCamera(localSurfaceView)
        }
    }

    // streaming section
    private fun initSurfaceView(view : SurfaceViewRenderer) {
        view.run {
            setMirror(false)
            setEnableHardwareScaler(true)
//            holder.setFormat(PixelFormat.TRANSPARENT)
            init(eglBaseContext, null)
        }
    }
    fun initRemoteSurfaceView(view : SurfaceViewRenderer) {
        this.remoteSurfaceView = view
        initSurfaceView(view)
    }
    fun initLocalSurfaceView(localView: SurfaceViewRenderer) {
        this.localSurfaceView = localView
        initSurfaceView(localView)
        startLocalStreaming(localView)
    }
    private fun startLocalStreaming(localView: SurfaceViewRenderer) {
        localStream = peerConnectionFactory.createLocalMediaStream(localStreamId)

        // 화상 통화이므로 바로 카메라 캡쳐 시작
        startCapturingCamera(localView)

        localAudioTrack = peerConnectionFactory.createAudioTrack(localTrackId + "_audio", localAudioSource)
        localStream?.addTrack(localAudioTrack)
        peerConnection?.addStream(localStream)
    }

    // localVideoTrack 초기화
    private fun startCapturingCamera(localView: SurfaceViewRenderer) {
        surfaceTextureHelper = SurfaceTextureHelper.create(
            Thread.currentThread().name, eglBaseContext
        )

        videoCapturer.initialize(
            surfaceTextureHelper, context, localVideoSource.capturerObserver
        )

        videoCapturer.startCapture(
            720, 480, 20
        )

        localVideoTrack = peerConnectionFactory.createVideoTrack(localTrackId + "_video", localVideoSource)
        localVideoTrack?.addSink(localView)
        localStream?.addTrack(localVideoTrack)
    }
    private fun getVideoCapturer(context: Context): CameraVideoCapturer =
        Camera2Enumerator(context).run {
            deviceNames.find {
                isFrontFacing(it)
            }?.let {
                createCapturer(it, null)
            }?:throw IllegalStateException()
        }
    private fun stopCapturingCamera() {
        videoCapturer.dispose()
        localVideoTrack?.removeSink(localSurfaceView)
        localSurfaceView.clearImage()
        localStream?.removeTrack(localVideoTrack)
        localVideoTrack?.dispose()
    }

    // screen capture section
    fun setPermissionIntent(screenPermissionIntent: Intent) {
        this.permissionIntent = screenPermissionIntent
    }

    fun startScreenCapturing() {
        Log.d("screen-share", "ScreenCapturing 진입")
        val displayMetrics = DisplayMetrics()
        val windowsManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        windowsManager.defaultDisplay.getMetrics(displayMetrics)

        val screenWidthPixels = displayMetrics.widthPixels
        val screenHeightPixels = displayMetrics.heightPixels

        surfaceTextureHelper = SurfaceTextureHelper.create(
            Thread.currentThread().name, eglBaseContext
        )
        Log.d("screen-share", "ScreenCapturing 중간")

        screenCapturer = createScreenCapturer()
        Log.d("screen-share", "screen capturer 1")

        screenCapturer!!.initialize(
            surfaceTextureHelper, context, localScreenVideoSource.capturerObserver
        )
        Log.d("screen-share", "screen capturer 2")

        screenCapturer!!.startCapture(screenWidthPixels, screenHeightPixels, 15)

        Log.d("screen-share", "ScreenCapturer start capture")

        localScreenShareVideoTrack =
            peerConnectionFactory.createVideoTrack(localTrackId + "_video", localScreenVideoSource)
        localScreenShareVideoTrack?.addSink(localSurfaceView)
        localStream?.addTrack(localScreenShareVideoTrack)
        peerConnection?.addStream(localStream)
        Log.d("screen-share", "ScreenCapturing 끝")
    }

    fun stopScreenCapturing() {
        screenCapturer?.stopCapture()
        screenCapturer?.dispose()
        localScreenShareVideoTrack?.removeSink(localSurfaceView)
        localSurfaceView.clearImage()
        localStream?.removeTrack(localScreenShareVideoTrack)
        localScreenShareVideoTrack?.dispose()
    }

    private fun createScreenCapturer(): VideoCapturer {
        return ScreenCapturerAndroid(permissionIntent, object : MediaProjection.Callback() {
            override fun onStop() {
                super.onStop()
                Log.d("permissions", "onStop: permission of screen casting is stopped")
            }
        })
    }

    interface Listener {
        fun onTransferEventToSocket(data: DataModel)
    }

    interface ReceiverListener{
        fun onDataReceived(it:DataChannel.Buffer)
    }
}