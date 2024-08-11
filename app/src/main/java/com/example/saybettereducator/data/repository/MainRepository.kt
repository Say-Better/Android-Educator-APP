package com.example.saybettereducator.data.repository

import com.example.saybettereducator.domain.model.DataModel
import com.example.saybettereducator.domain.model.DataModelType.*
import com.example.saybettereducator.domain.model.UserStatus
import com.example.saybettereducator.data.api.helper.FirebaseClient
import com.example.saybettereducator.utils.webrtcObserver.MyPeerObserver
import com.example.saybettereducator.data.api.helper.WebRTCClient
import com.google.gson.Gson
import org.webrtc.IceCandidate
import org.webrtc.MediaStream
import org.webrtc.PeerConnection
import org.webrtc.SessionDescription
import org.webrtc.SurfaceViewRenderer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainRepository @Inject constructor(
    private val firebaseClient : FirebaseClient,
    private val webRTCClient: WebRTCClient,
    private val gson : Gson
) : WebRTCClient.Listener {
    var listener : Listener? = null
    private var target : String? = null
    private var remoteView: SurfaceViewRenderer? = null


    fun login(userid : String, isDone : (Boolean, String?) -> Unit) {
        firebaseClient.login(userid, isDone)
    }

    fun setTarget(target: String) {
        this.target = target
    }

    fun initFirebase() {
        firebaseClient.subscribeForLatestEvent(object : FirebaseClient.Listener {
            override fun onLatestEventReceived(event: DataModel) {
                listener?.onLatestEventReceived(event)
                when(event.type) {
                    Offer -> {
                        // Offer 받으면 SessionDescription 생성
                        webRTCClient.onRemoteSessionReceived(
                            SessionDescription(
                                SessionDescription.Type.OFFER,
                                event.data.toString()
                            )
                        )
                        // Offer 받았으므로 다시 Answer 전달해야함
                        webRTCClient.answer(target!!)
                    }
                    Answer -> {
                        // 전화를 거는 입장에서 회신을 받은 상황, Answer 형태의 SessionDescription 생성
                        webRTCClient.onRemoteSessionReceived(
                            SessionDescription(
                                SessionDescription.Type.ANSWER,
                                event.data.toString()
                            )
                        )
                    }
                    IceCandidates -> {
                        val candidate: IceCandidate? = try {
                            gson.fromJson(event.data.toString(), IceCandidate::class.java)
                        } catch (e: Exception) {
                            null
                        }
                        candidate?.let {
                            webRTCClient.addIceCandidateToPeer(it)
                        }
                    }
                    EndCall -> {
                        listener?.endCall()
                    }
                    else -> Unit
                }
            }

        })
    }

    fun initWebrtcClient(userid: String) {
        webRTCClient.listener = this
        webRTCClient.initializeWebrtcClient(userid, object : MyPeerObserver() {
            override fun onAddStream(p0: MediaStream?) {
                super.onAddStream(p0)
                try {
                    p0?.videoTracks?.get(0)?.addSink(remoteView)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

            override fun onIceCandidate(p0: IceCandidate?) {
                super.onIceCandidate(p0)
                p0?.let {
                    webRTCClient.sendIceCandidate(target!!, it)
                }
            }

            override fun onConnectionChange(newState: PeerConnection.PeerConnectionState?) {
                super.onConnectionChange(newState)
                if(newState == PeerConnection.PeerConnectionState.CONNECTED) {
                    // 1. change my status to in call
                    changeMyStatus(UserStatus.IN_CALL)
                    // 2. clear latest event inside my user section in firebase database
                    firebaseClient.clearLatestEvent()
                }
            }
        })

    }

    // firebase에서의 상태를 업데이트
    private fun changeMyStatus(status : UserStatus) {
        firebaseClient.changeMyStatus(status)
    }

    fun sendConnectionRequest(target : String, success : (Boolean) -> Unit) {
        firebaseClient.sendMessageToOtherClient(
            DataModel(
                type = StartVideoCall,
                target = target
            ), success
        )
    }
    fun initLocalSurfaceView(view: SurfaceViewRenderer) {
        webRTCClient.initLocalSurfaceView(view)
    }
    fun initRemoteSurfaceView(view: SurfaceViewRenderer) {
        webRTCClient.initRemoteSurfaceView(view)
        this.remoteView = view
    }

    interface Listener {
        fun onLatestEventReceived(data : DataModel)
        fun endCall()
    }

    override fun onTransferEventToSocket(data: DataModel) {
        firebaseClient.sendMessageToOtherClient(data){}
    }

    fun startCall() {
        webRTCClient.call(target!!)
    }

    fun sendEndCall() {
        onTransferEventToSocket(
            DataModel(
                type = EndCall,
                target = target!!
            )
        )
    }

    fun endCall() {
        webRTCClient.closeConnection()
        //통화 종료시 다시 온라인 상태로 돌아감
        changeMyStatus(UserStatus.ONLINE)
    }

    fun switchCamera() {
        webRTCClient.switchCamera()
    }

    fun toggleAudio(shouldBeMuted : Boolean) {
        webRTCClient.toggleAudio(shouldBeMuted)
    }

    fun toggleVideo(shouldBeMuted: Boolean) {
        webRTCClient.toggleVideo(shouldBeMuted)
    }
}