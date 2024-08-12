package com.example.saybettereducator.di

import android.app.Application
import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.Locale
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTextToSpeech(@ApplicationContext context: Context): TextToSpeech {
        return TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                Log.d("TextToSpeech", "Initialization successful")
            } else {
                Log.e("TextToSpeech", "Initialization failed")
            }
        }.apply {
            language = Locale.KOREA
        }
    }
}


