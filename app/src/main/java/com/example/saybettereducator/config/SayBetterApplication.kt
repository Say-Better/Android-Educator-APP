package com.example.saybettereducator.config

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SayBetterApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
