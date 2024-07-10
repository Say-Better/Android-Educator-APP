package com.example.saybettereducator.utils.webrtc.service

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import javax.inject.Inject

class MainServiceRepository @Inject constructor(
    private val context : Context
) {
    val TAG : String = "ServiceDebug"

    @RequiresApi(Build.VERSION_CODES.O)
    fun startService(userid : String) {
        Thread{
            val intent = Intent(context, MainService::class.java)
            intent.putExtra("userid", userid)
            intent.action = MainServiceActions.START_SERVICE.name
            startServiceIntent(intent)
        }.start()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setupViews(caller: Boolean, target: String) {
        val intent = Intent(context, MainService::class.java)
        intent.apply {
            action = MainServiceActions.SETUP_VIEWS.name
            putExtra("target", target)
            putExtra("isCaller", caller)
        }
        startServiceIntent(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startServiceIntent(intent : Intent) {
        //Foreground service: 시스템에 의해 종료될 확률이 적음
        context.startForegroundService(intent)
    }

}