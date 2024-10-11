package com.example.saybettereducator.data.api.helper

import android.util.Log
import com.example.saybettereducator.data.model.DataModel
import com.example.saybettereducator.data.model.UserStatus
import com.example.saybettereducator.data.model.FirebaseFieldNames.LATEST_EVENT
import com.example.saybettereducator.data.model.FirebaseFieldNames.STATUS
import com.example.saybettereducator.utils.MyEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.gson.Gson
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseClient @Inject constructor(
    private val dbRef : DatabaseReference,
    private val gson : Gson
) {

    private var currentUserid : String? = null
    private fun setUserid(userid : String) {
        Log.d("FirebaseClient", "userId: $userid")
        this.currentUserid = userid
    }

    fun login(userid : String, done : (Boolean, String?) -> Unit) {
        dbRef.addListenerForSingleValueEvent(object : MyEventListener() {
            override fun onDataChange(snapshot: DataSnapshot) {
                //입력한 id가 이미 존재하는 경우
                if(snapshot.hasChild(userid)) {
                    dbRef.child(userid).child(STATUS).setValue(UserStatus.ONLINE)
                        .addOnCompleteListener {
                            setUserid(userid)
                            done(true, null)
                        }.addOnFailureListener {
                            done(false, "${it.message}")
                        }
                }
                else {
                    dbRef.child(userid).child(STATUS).setValue(UserStatus.ONLINE)
                        .addOnCompleteListener {
                            setUserid(userid)
                            done(true, null)
                        }.addOnFailureListener {
                            done(false, "${it.message}")
                        }
                }
            }
        })
    }

    //자신의 Latest Event 항목의 변화를 청취
    fun subscribeForLatestEvent(listener : Listener){
        try{
            Log.d("FirebaseClient", "currUID: $currentUserid")
            dbRef.child(currentUserid!!).child(LATEST_EVENT).addValueEventListener(
                object : MyEventListener() {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        super.onDataChange(snapshot)
                        val snapshotValueString : String = snapshot.value.toString()
                        Log.d("FirebaseClient", "snapshot value: $snapshotValueString")
                        val event  = try {
                            gson.fromJson(snapshot.value.toString(), DataModel::class.java)
                        }catch (e : Exception) {
                            e.printStackTrace()
                            null
                        }
                        Log.d("FirebaseClient", "event: $event")

                        event?.let {
                            listener.onLatestEventReceived(it)
                        }
                    }
                }
            )
        }catch (e : Exception) {
            e.printStackTrace()
            Log.d("Firebase Listener", "Something Wrong")
        }
    }

    fun sendMessageToOtherClient(message : DataModel, success : (Boolean) -> Unit){
        val convertedMessage = gson.toJson(message.copy(sender = currentUserid))
        dbRef.child(message.target).child(LATEST_EVENT).setValue(convertedMessage)
            .addOnCompleteListener {
                success(true)
            }.addOnFailureListener {
                success(false)
            }
    }

    fun changeMyStatus(status: UserStatus) {
        dbRef.child(currentUserid!!).child(STATUS).setValue(status.name)
    }

    fun clearLatestEvent() {
        dbRef.child(currentUserid!!).child(LATEST_EVENT).setValue(null)
    }

    fun logOff(function : () -> Unit) {
        dbRef.child(currentUserid!!).child(STATUS).setValue(UserStatus.OFFLINE)
            .addOnCompleteListener { function() }
    }

    interface Listener {
        fun onLatestEventReceived(event : DataModel)
    }

}